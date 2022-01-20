package com.chan.alarm.feature.data.source

import com.chan.alarm.feature.data.AlarmDao
import com.chan.alarm.feature.data.AlarmTable
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataBaseSourceImpl @Inject constructor(
    private val alarmDao: AlarmDao
) : DataBaseSource {

    override suspend fun insert(alarmTable: AlarmTable) =
        withContext(dispatcher) {
            alarmDao.insert(alarmTable)
        }

    override suspend fun delete(id: Int) {
        withContext(dispatcher) {
            alarmDao.delete(id)
        }
    }

    override suspend fun select(): List<AlarmTable> =
        withContext(dispatcher) {
            alarmDao.select()
        }

    override suspend fun selectId(id: Int): AlarmTable =
        withContext(dispatcher) {
            alarmDao.selectId(id)
        }

    override suspend fun selectName(alarmName: String): AlarmTable =
        withContext(dispatcher) {
            alarmDao.selectAlarmName(alarmName)
        }

    override suspend fun update(alarmTable: AlarmTable) =
        withContext(dispatcher) {
            alarmDao.update(alarmTable)
        }

}