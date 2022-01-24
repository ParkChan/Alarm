package com.chan.alarm.feature.domain.data

data class Alarm(
    val id: Int = 0,
    val name: String = "",
    val timeStamp: Long = 0L,
    val enable: Boolean = false,
    val ringtoneUri: String = ""
)
