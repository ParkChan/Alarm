package com.chan.alarm.common.presentation.vo

import android.net.Uri
import android.os.Parcelable
import androidx.core.net.toUri
import com.chan.alarm.feature.data.MapToDomain
import com.chan.alarm.feature.domain.data.Alarm
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlarmVo(
    var id: Int = 0,
    var name: String = "",
    var timeStamp: Long = 0L,
    var enable: Boolean = false,
    var ringtoneUri: String = ""
) : MapToDomain<Alarm>, Parcelable {
    fun getUri(): Uri = ringtoneUri.toUri()

    override fun mapToDomain(): Alarm =
        Alarm(
            id = id,
            name = name,
            timeStamp = timeStamp,
            enable = enable,
            ringtoneUri = ringtoneUri
        )

    companion object {
        fun mapFromDomain(alarm: Alarm): AlarmVo = AlarmVo(
            alarm.id,
            alarm.name,
            alarm.timeStamp,
            alarm.enable,
            alarm.ringtoneUri
        )
    }
}
