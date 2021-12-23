package com.chan.alarm.feature.database.domain.data

data class Alarm(
    val id: Int = 0,
    val alarmName: String,
    val timeStamp: Long,
    val isAlarm: Boolean,
    val ringtoneUri: String
)
