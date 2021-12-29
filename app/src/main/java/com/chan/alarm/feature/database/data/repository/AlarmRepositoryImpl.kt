package com.chan.alarm.feature.database.data.repository

import com.chan.alarm.feature.database.data.AlarmTable
import com.chan.alarm.feature.database.data.source.DataBaseSource
import com.chan.alarm.feature.database.domain.data.Alarm
import com.chan.alarm.feature.database.domain.repository.AlarmRepository
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val dataBaseSource: DataBaseSource
) : AlarmRepository {
    override suspend fun insert(alarm: Alarm) {
        dataBaseSource.insert(mapToAlarmTable(alarm))
    }

    override suspend fun select(): List<Alarm> =
        dataBaseSource.select().map(AlarmTable::mapToDomain)

    override suspend fun selectId(id: Int): Alarm =
        dataBaseSource.selectId(id).mapToDomain()

    override suspend fun selectAlarmName(alarmName: String): Alarm =
        dataBaseSource.selectName(alarmName).mapToDomain()

    override suspend fun update(alarm: Alarm) =
        dataBaseSource.update(mapToAlarmTable(alarm))

    companion object {
        private fun mapToAlarmTable(alarm: Alarm) =
            AlarmTable(
                id = alarm.id,
                alarmName = alarm.alarmName,
                timeStamp = alarm.timeStamp,
                enableAlarm = alarm.enableAlarm,
                ringtoneUri = alarm.ringtoneUri
            )

    }
}