package com.chan.alarm.feature.domain.repository

import com.chan.alarm.feature.domain.data.Alarm

interface AlarmRepository {
    suspend fun insert(alarm: Alarm)
    suspend fun delete(id: Int)
    suspend fun select(): List<Alarm>
    suspend fun selectId(id: Int): Alarm
    suspend fun selectAlarmName(alarmName: String): Alarm
    suspend fun update(alarm: Alarm)
}