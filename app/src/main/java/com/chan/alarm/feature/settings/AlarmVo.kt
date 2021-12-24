package com.chan.alarm.feature.settings

import android.net.Uri
import android.os.Parcelable
import androidx.core.net.toUri
import com.chan.alarm.feature.database.domain.data.Alarm
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

    companion object {
        const val BUNDLE_KEY = "BUNDLE_KEY_ALARM_VO"
        fun mapToAlarmVo(alarm: Alarm) = AlarmVo(
            alarm.id,
            alarm.alarmName,
            alarm.timeStamp,
            alarm.isAlarm,
            alarm.ringtoneUri
        )
    }
}
