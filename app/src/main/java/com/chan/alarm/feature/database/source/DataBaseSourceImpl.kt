package com.chan.alarm.feature.database.source

import com.chan.alarm.feature.database.AlarmDao
import com.chan.alarm.feature.database.AlarmTable
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataBaseSourceImpl @Inject constructor(
    private val alarmDao: AlarmDao
) : DataBaseSource {

    override suspend fun insert(alarmTable: AlarmTable) =
        withContext(dispatcher) {
            alarmDao.insert(alarmTable)
        }

    override suspend fun select(): List<AlarmTable> =
        withContext(dispatcher) {
            alarmDao.select()
        }

}