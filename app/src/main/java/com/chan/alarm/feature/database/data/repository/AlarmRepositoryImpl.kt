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

    override suspend fun select(): List<Alarm> = dataBaseSource.select().map {
        it.mapToDomain()
    }

    companion object {
        private fun mapToAlarmTable(alram: Alarm) =
            AlarmTable(
                id = alram.id,
                alarmName = alram.alarmName,
                timeStamp = alram.timeStamp,
                isAlarm = alram.isAlarm,
                ringtoneUri = alram.ringtoneUri
            )

    }
}