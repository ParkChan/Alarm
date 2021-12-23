package com.chan.alarm.feature.database.source

import com.chan.alarm.feature.database.AlarmTable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DataBaseSource {

    val dispatcher: CoroutineDispatcher
        get() = Dispatchers.IO

    suspend fun insert(alarmTable: AlarmTable)
    suspend fun select(): List<AlarmTable>
}