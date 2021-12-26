package com.chan.alarm.feature.settings

import android.net.Uri
import android.os.Parcelable
import androidx.core.net.toUri
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlarmVo(
    var id: Int = 0,
    var alarmName: String = "",
    var timeStamp: Long = 0L,
    var isAlarm: Boolean = false,
    var ringtoneUri: String = ""
) : Parcelable {
    fun getUri(): Uri = ringtoneUri.toUri()
}
