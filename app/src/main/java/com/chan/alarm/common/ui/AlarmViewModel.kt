package com.chan.alarm.common.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chan.alarm.common.ui.AlarmReceiver.Companion.BUNDLE_KEY_ALARM_ID
import com.chan.alarm.feature.database.domain.data.Alarm
import com.chan.alarm.feature.database.domain.usecase.AlarmDataBaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val alarmDataBaseUseCase: AlarmDataBaseUseCase
) : ViewModel() {

    private val _alarms = MutableLiveData<List<Alarm>>()
    val alarms: LiveData<List<Alarm>> get() = _alarms

    private val _wakeAlarm = MutableLiveData<Alarm>()
    val wakeAlarm: LiveData<Alarm> get() = _wakeAlarm

    private val _addAlarmEvent = MutableLiveData<Alarm>()
    val addAlarmEvent: LiveData<Alarm> get() = _addAlarmEvent

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception.message)
    }

    fun saveAlarm(alarm: Alarm) = viewModelScope.launch(coroutineExceptionHandler) {
        alarmDataBaseUseCase.insert(alarm)
        getAlarmList()
        _addAlarmEvent.value = alarmDataBaseUseCase.select().getOrNull()?.last()
    }

    fun getAlarmList() = viewModelScope.launch(coroutineExceptionHandler) {
        alarmDataBaseUseCase.select().onSuccess {
            _alarms.value = it
        }.onFailure {
            Timber.e(it.message)
        }
    }

    fun selectAlarmInfo(alarmId: Int) = viewModelScope.launch(coroutineExceptionHandler) {
        alarmDataBaseUseCase.selectId(alarmId).onSuccess {
            _wakeAlarm.value = it
        }.onFailure {
            Timber.e(it.message)
        }
    }

    fun updateAlarm(alarm: Alarm) =
        viewModelScope.launch(coroutineExceptionHandler) {
            alarmDataBaseUseCase.update(alarm)
            getAlarmList()
        }

    fun addBroadCastAlarmManager(context: Context, alarm: Alarm) {
        Timber.d(">>>>>>>> addBroadCastAlarmManager")
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val bundle = Bundle()
        bundle.putInt(BUNDLE_KEY_ALARM_ID, alarm.id)
        intent.putExtras(bundle)

        val pendingIntent =
            PendingIntent.getBroadcast(context, alarm.id, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val alarmClockInfo = AlarmManager.AlarmClockInfo(alarm.timeStamp, pendingIntent)
        alarmManager.setAlarmClock(alarmClockInfo, pendingIntent)
    }

    fun cancelBroadCastAlarmManager(context: Context, alarmId: Int) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, alarmId, intent, PendingIntent.FLAG_NO_CREATE)
        pendingIntent?.cancel()
    }

    fun onClickCheckBox(context: Context, isCheck: Boolean, alarm: Alarm) {
        viewModelScope.launch(coroutineExceptionHandler) {
            Timber.d(" isCheck $isCheck")
            if (isCheck) {
                alarm.isAlarm = true
                addBroadCastAlarmManager(context, alarm)
            } else {
                alarm.isAlarm = false
                cancelBroadCastAlarmManager(context, alarm.id)
            }
            updateAlarm(alarm)
        }
    }
}