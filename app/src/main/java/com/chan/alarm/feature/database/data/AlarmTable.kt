package com.chan.alarm.feature.database.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chan.alarm.feature.database.domain.data.Alarm

@Entity(tableName = "alarm")
data class AlarmTable(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val alarmName: String,
    val timeStamp: Long,
    val enableAlarm: Boolean,
    val ringtoneUri: String
) : MapToDomain<Alarm> {
    override fun mapToDomain(): Alarm =
        Alarm(
            id = id,
            alarmName = alarmName,
            timeStamp = timeStamp,
            enableAlarm = enableAlarm,
            ringtoneUri = ringtoneUri
        )
}
