package com.chan.alarm.common.ui.util

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {

    private const val NEXT_DAY = 1
    const val FORMAT_TYPE_HH_MM_AA = "HH:mm aa"
    const val FORMAT_TYPE_HH_MM = "HH:mm"
    private val timeZone = TimeZone.getDefault()

    fun nextDayTimeMills(hour: Int, minute: Int): Long {
        return Calendar.getInstance(timeZone).apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            if (before(Calendar.getInstance())) {
                add(Calendar.DATE, NEXT_DAY)
            }
        }.timeInMillis
    }

    fun isYesterday(timeStamp: Long): Boolean {
        return Calendar.getInstance().timeInMillis > timeStamp
    }

    fun nextDayTimeMills(timeStamp: Long): Long {
        return Calendar.getInstance(timeZone).apply {
            add(Calendar.DATE, NEXT_DAY)
        }.timeInMillis
    }

    fun calendar(): Calendar = Calendar.getInstance(timeZone)

    fun convertAlarmDisplayTime(format: String, timeStamp: Long): String {
        val date = Date().apply {
            time = timeStamp
        }
        val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
        return simpleDateFormat.format(date)
    }


}