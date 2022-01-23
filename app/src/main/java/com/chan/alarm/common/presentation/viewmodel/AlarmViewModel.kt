package com.chan.alarm.common.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chan.alarm.common.presentation.AlarmEvent
import com.chan.alarm.common.presentation.util.TimeUtil
import com.chan.alarm.common.presentation.vo.AlarmVo
import com.chan.alarm.feature.domain.data.Alarm
import com.chan.alarm.feature.domain.usecase.AlarmDataBaseUseCase
import com.chan.ui.livedata.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val alarmDataBaseUseCase: AlarmDataBaseUseCase
) : ViewModel() {

    private val _alarms = MutableLiveData<Event<List<AlarmVo>>>()
    val alarms: LiveData<Event<List<AlarmVo>>> get() = _alarms

    private val _displayAlarm = MutableLiveData<Event<AlarmVo>>()
    val displayAlarm: LiveData<Event<AlarmVo>> get() = _displayAlarm

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception.message)
    }

    fun saveAlarm(alarm: Alarm) = viewModelScope.launch(coroutineExceptionHandler) {
        alarmDataBaseUseCase.insert(alarm)
    }

    fun selectAlarmList() = viewModelScope.launch(coroutineExceptionHandler) {
        alarmDataBaseUseCase.select()
            .onSuccess { alarms ->
                _alarms.value = Event(alarms.map { alarm -> AlarmVo.mapFromDomain(alarm) })
            }.onFailure {
                Timber.e(it.message)
            }
    }

    private fun updateAlarm(alarm: AlarmVo) =
        viewModelScope.launch(coroutineExceptionHandler) {
            alarmDataBaseUseCase.update(alarm.mapToDomain())
        }

    fun selectAlarmId(alarmId: Int) =
        viewModelScope.launch(coroutineExceptionHandler) {
            _displayAlarm.value =
                Event(
                    AlarmVo.mapFromDomain(
                        alarmDataBaseUseCase.selectId(alarmId).getOrNull() ?: Alarm()
                    )
                )
        }

    suspend fun selectAlarmName(alarmName: String): AlarmVo = withContext(
        viewModelScope.coroutineContext
    ) {
        AlarmVo.mapFromDomain(
            alarmDataBaseUseCase.selectAlarmName(alarmName).getOrNull() ?: Alarm()
        )
    }

    suspend fun offAlarm(alarm: AlarmVo) =
        viewModelScope.launch(coroutineExceptionHandler) {
            alarmDataBaseUseCase.update(
                alarm.apply { enable = false }.mapToDomain()
            )
        }

    suspend fun addAlarm(alarm: Alarm) = viewModelScope.launch(coroutineExceptionHandler) {
        alarmDataBaseUseCase.insert(alarm)
    }

    fun deleteAlarm(id: Int) = viewModelScope.launch(coroutineExceptionHandler) {
        alarmDataBaseUseCase.delete(id)
    }

    fun onClickCheckBox(context: Context, isChecked: Boolean, alarm: AlarmVo) =
        viewModelScope.launch(coroutineExceptionHandler) {
            Timber.d(">>> onClickCheckBox alarm $alarm")
            val alarmData = alarm.apply {
                enable = isChecked
                timeStamp = if (isChecked) {
                    if (TimeUtil.isBeforeTimeInMillis(
                            Calendar.getInstance().timeInMillis,
                            timeStamp
                        )
                    ) {
                        TimeUtil.nextDayTimeInMillis(timeStamp)
                    } else {
                        timeStamp
                    }
                } else {
                    timeStamp
                }
            }
            updateAlarm(alarmData)

            if (isChecked) {
                AlarmEvent.addBroadCastAlarmManager(context, alarmData)
            } else {
                AlarmEvent.cancelBroadCastAlarmManager(context, alarmData.id)
            }
        }

}