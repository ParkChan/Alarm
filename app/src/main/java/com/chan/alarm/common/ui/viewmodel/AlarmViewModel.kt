package com.chan.alarm.common.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chan.alarm.common.ui.AlarmEvent
import com.chan.alarm.feature.database.domain.data.Alarm
import com.chan.alarm.feature.database.domain.usecase.AlarmDataBaseUseCase
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

    private val _alarms = MutableLiveData<List<Alarm>>()
    val alarms: LiveData<List<Alarm>> get() = _alarms

    private val _displayAlarm = MutableLiveData<Alarm>()
    val displayAlarm: LiveData<Alarm> get() = _displayAlarm

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception.message)
    }

    fun saveAlarm(alarm: Alarm) = viewModelScope.launch(coroutineExceptionHandler) {
        alarmDataBaseUseCase.insert(alarm)
    }

    fun selectAlarmList() = viewModelScope.launch(coroutineExceptionHandler) {
        alarmDataBaseUseCase.select()
            .onSuccess {
                _alarms.value = it
            }.onFailure {
                Timber.e(it.message)
            }
    }

    private fun updateAlarm(alarm: Alarm) =
        viewModelScope.launch(coroutineExceptionHandler) {
            alarmDataBaseUseCase.update(alarm)
        }

    suspend fun notiAlarmInfo(alarmId: Int) =
        viewModelScope.launch(coroutineExceptionHandler) {
            _displayAlarm.value = alarmDataBaseUseCase.selectId(alarmId).getOrNull()
        }

    suspend fun offAlarmInfo(alarm: Alarm) =
        viewModelScope.launch(coroutineExceptionHandler) {
            alarmDataBaseUseCase.update(alarm.apply { isAlarm = false })
        }

    fun addAlarm(alarm: Alarm) = viewModelScope.launch(coroutineExceptionHandler) {
        alarmDataBaseUseCase.insert(alarm)
    }

    suspend fun getAlarm(alarmName: String) =
        withContext(viewModelScope.coroutineContext) {
            alarmDataBaseUseCase.selectAlarmName(alarmName).getOrNull() ?: Alarm()
        }

    fun onClickCheckBox(context: Context, isCheck: Boolean, alarm: Alarm) {
        val alarmData = alarm.apply { isAlarm = isCheck }

        updateAlarm(alarmData)
        if (isCheck) {
            AlarmEvent.addBroadCastAlarmManager(context, alarmData)
        } else {
            AlarmEvent.cancelBroadCastAlarmManager(context, alarmData.id)
        }

    }
}