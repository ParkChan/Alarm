package com.chan.alarm.feature.database.data.source

import com.chan.alarm.feature.database.data.AlarmDao
import com.chan.alarm.feature.database.data.AlarmTable
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

    override suspend fun selectId(id: Int): AlarmTable =
        withContext(dispatcher) {
            alarmDao.selectId(id)
        }

    override suspend fun update(alarmTable: AlarmTable)  =
        withContext(dispatcher) {
            alarmDao.update(alarmTable)
        }

}