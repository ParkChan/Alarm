package com.chan.alarm.feature.database.domain.usecase

import com.chan.alarm.feature.database.domain.data.Alarm
import com.chan.alarm.feature.database.domain.repository.AlarmRepository
import javax.inject.Inject

class AlarmDataBaseUseCaseImpl @Inject constructor(
    private val repository: AlarmRepository
) : AlarmDataBaseUseCase {

    override suspend fun insert(alarm: Alarm) = repository.insert(alarm)

    override suspend fun delete(id: Int) {
        repository.delete(id)
    }

    override suspend fun select(): Result<List<Alarm>> = runCatching {
        repository.select()
    }

    override suspend fun selectId(id: Int): Result<Alarm> = runCatching {
        repository.selectId(id)
    }

    override suspend fun selectAlarmName(alarmName: String) = runCatching {
        repository.selectAlarmName(alarmName)
    }

    override suspend fun update(alarm: Alarm) = repository.update(alarm)

}