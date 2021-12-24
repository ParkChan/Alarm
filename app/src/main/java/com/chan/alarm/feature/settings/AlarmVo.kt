package com.chan.alarm.feature.settings

import android.net.Uri
import androidx.core.net.toUri

data class AlarmVo(
    var alarmName: String = "",
    var timeStamp: Long = 0L,
    var isAlarm: Boolean = false,
    var ringtoneUri: String = ""
) {
    fun getUri(): Uri = ringtoneUri.toUri()
}
