package com.chan.alarm.feature.database.domain.data

data class Alarm(
    val id: Int = 0,
    val alarmName: String = "",
    val timeStamp: Long = 0L,
    var isAlarm: Boolean = false,
    val ringtoneUri: String = ""
)
