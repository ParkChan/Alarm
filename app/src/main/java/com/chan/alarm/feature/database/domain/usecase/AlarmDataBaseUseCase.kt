package com.chan.alarm.feature.database.domain.usecase

import com.chan.alarm.feature.database.domain.data.Alarm

interface AlarmDataBaseUseCase {
    suspend fun insert(alarm: Alarm)
    suspend fun delete(id: Int)
    suspend fun select(): Result<List<Alarm>>
    suspend fun selectId(id: Int): Result<Alarm>
    suspend fun selectAlarmName(alarmName: String): Result<Alarm>
    suspend fun update(alarm: Alarm)
}