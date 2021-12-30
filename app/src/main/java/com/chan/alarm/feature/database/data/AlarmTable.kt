package com.chan.alarm.feature.database.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chan.alarm.feature.database.domain.data.Alarm

@Entity(tableName = "alarm")
data class AlarmTable(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val timeStamp: Long,
    val enable: Boolean,
    val ringtoneUri: String
) : MapToDomain<Alarm> {
    override fun mapToDomain(): Alarm =
        Alarm(
            id = id,
            name = name,
            timeStamp = timeStamp,
            enable = enable,
            ringtoneUri = ringtoneUri
        )
}
