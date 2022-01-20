package com.chan.alarm.feature.domain.data

data class Alarm(
    val id: Int = 0,
    val name: String = "",
    var timeStamp: Long = 0L,
    var enable: Boolean = false,
    val ringtoneUri: String = ""
)
