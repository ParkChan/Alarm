package com.chan.alarm.common.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chan.alarm.common.ui.AlarmEvent
import com.chan.alarm.common.ui.util.TimeUtil
import com.chan.alarm.feature.database.domain.data.Alarm
import com.chan.alarm.feature.database.domain.usecase.AlarmDataBaseUseCase
import com.chan.ui.livedata.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val alarmDataBaseUseCase: AlarmDataBaseUseCase
) : ViewModel() {

    private val _alarms = MutableLiveData<Event<List<Alarm>>>()
    val alarms: LiveData<Event<List<Alarm>>> get() = _alarms

    private val _displayAlarm = MutableLiveData<Event<Alarm>>()
    val displayAlarm: LiveData<Event<Alarm>> get() = _displayAlarm

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception.message)
    }

    fun saveAlarm(alarm: Alarm) = viewModelScope.launch(coroutineExceptionHandler) {
        alarmDataBaseUseCase.insert(alarm)
    }

    fun selectAlarmList() = viewModelScope.launch(coroutineExceptionHandler) {
        alarmDataBaseUseCase.select()
            .onSuccess {
                _alarms.value = Event(it)
            }.onFailure {
                Timber.e(it.message)
            }
    }

    private fun updateAlarm(alarm: Alarm) =
        viewModelScope.launch(coroutineExceptionHandler) {
            alarmDataBaseUseCase.update(alarm)
        }

    suspend fun selectAlarm(alarmId: Int) =
        viewModelScope.launch(coroutineExceptionHandler) {
            _displayAlarm.value =
                Event(
                    alarmDataBaseUseCase.selectId(alarmId).getOrNull() ?: Alarm()
                )
        }

    suspend fun offAlarmInfo(alarm: Alarm) =
        viewModelScope.launch(coroutineExceptionHandler) {
            alarmDataBaseUseCase.update(alarm.apply { isAlarm = false })
        }

    suspend fun addAlarm(alarm: Alarm): Alarm = withContext(viewModelScope.coroutineContext) {
        alarmDataBaseUseCase.insert(alarm)
        alarmDataBaseUseCase.selectAlarmName(alarm.alarmName).getOrNull() ?: Alarm()
    }

    fun onClickCheckBox(context: Context, isCheck: Boolean, alarm: Alarm) =
        viewModelScope.launch(coroutineExceptionHandler) {
            val alarmData = alarm.apply { isAlarm = isCheck }
            updateAlarm(alarmData)
            if (isCheck) {
                if (TimeUtil.isYesterday(alarmData.timeStamp)) {
                    alarmDataBaseUseCase.update(
                        alarmData.apply {
                            timeStamp = TimeUtil.nextDayTimeMills(timeStamp)
                        }
                    )
                    AlarmEvent.addBroadCastAlarmManager(context, alarmData)
                } else {
                    AlarmEvent.addBroadCastAlarmManager(context, alarmData)
                }
            } else {
                AlarmEvent.cancelBroadCastAlarmManager(context, alarmData.id)
            }
        }
}