package com.chan.alarm.common.presentation.vo

import android.os.Parcelable
import com.chan.alarm.feature.data.MapToDomain
import com.chan.alarm.feature.domain.data.Alarm
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlarmVo(
    val id: Int = 0,
    val name: String = "",
    val timeStamp: Long = 0L,
    val enable: Boolean = false,
    val ringtoneUri: String = ""
) : MapToDomain<Alarm>, Parcelable {

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
