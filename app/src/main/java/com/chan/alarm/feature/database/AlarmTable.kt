package com.chan.alarm.feature.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm")
data class AlarmTable(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val alarmName: String,
    val timeStamp: Long,
    val isAlarm: Boolean,
    val ringtoneUri: String
)
