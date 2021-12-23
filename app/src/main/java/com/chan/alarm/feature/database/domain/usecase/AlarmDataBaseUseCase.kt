package com.chan.alarm.feature.database.domain.usecase

import com.chan.alarm.feature.database.domain.data.Alarm

interface AlarmDataBaseUseCase {
    suspend fun insert(alarm: Alarm)
    suspend fun select(): List<Alarm>
}