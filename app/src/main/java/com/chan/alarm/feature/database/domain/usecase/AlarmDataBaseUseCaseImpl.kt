package com.chan.alarm.feature.database.domain.usecase

import com.chan.alarm.feature.database.domain.data.Alarm
import com.chan.alarm.feature.database.domain.repository.AlarmRepository
import javax.inject.Inject

class AlarmDataBaseUseCaseImpl @Inject constructor(
    private val repository: AlarmRepository
) : AlarmDataBaseUseCase {

    override suspend fun insert(alarm: Alarm) = repository.insert(alarm)

    override suspend fun select(): List<Alarm> = repository.select()

}