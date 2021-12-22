package com.chan.room.source

import com.chan.room.AlarmDao
import com.chan.room.AlarmTable
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