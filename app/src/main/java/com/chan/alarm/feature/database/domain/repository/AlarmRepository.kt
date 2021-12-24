package com.chan.alarm.feature.database.domain.repository

import com.chan.alarm.feature.database.domain.data.Alarm

interface AlarmRepository {
    suspend fun insert(alarm: Alarm)
    suspend fun select(): List<Alarm>
    suspend fun selectId(id: Int): Alarm
    suspend fun update(alarm: Alarm)
}