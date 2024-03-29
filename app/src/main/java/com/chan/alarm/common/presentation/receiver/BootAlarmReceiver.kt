package com.chan.alarm.common.presentation.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.chan.alarm.common.presentation.AlarmEvent
import com.chan.alarm.common.presentation.vo.AlarmVo
import com.chan.alarm.feature.domain.usecase.AlarmDataBaseUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class BootAlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alarmDataBaseUseCase: AlarmDataBaseUseCase
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception.message)
    }

    override fun onReceive(context: Context, intent: Intent?) {
        intent?.action?.let { action ->
            if (action == Intent.ACTION_BOOT_COMPLETED ||
                action == Intent.ACTION_LOCKED_BOOT_COMPLETED
            ) {
                coroutineScope.launch(coroutineExceptionHandler) {
                    alarmDataBaseUseCase.select().onSuccess { alarmList ->
                        alarmList.forEach {
                            if (it.enable) {
                                Timber.d(">>> BootAlarmReceiver addAlarm $it")
                                AlarmEvent.addBroadCastAlarmManager(
                                    context,
                                    AlarmVo.mapFromDomain(it)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}