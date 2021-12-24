package com.chan.alarm.feature.database.data.source

import com.chan.alarm.feature.database.data.AlarmTable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DataBaseSource {

    val dispatcher: CoroutineDispatcher
        get() = Dispatchers.IO

    suspend fun insert(alarmTable: AlarmTable)
    suspend fun select(): List<AlarmTable>
    suspend fun selectId(id: Int): AlarmTable
    suspend fun update(alarmTable: AlarmTable)

}