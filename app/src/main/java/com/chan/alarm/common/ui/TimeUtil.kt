package com.chan.alarm.common.ui

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {

    private const val NEXT_DAY = 1
    private const val FORMATTER = "HH:mm"
    private const val AM = "AM"
    private const val PM = "PM"
    private val timeZone = TimeZone.getDefault()

    fun convertAlarmTimeMills(hour: Int, minute: Int): Long =
        Calendar.getInstance(timeZone).apply {
            set(Calendar.HOUR, hour)
            set(Calendar.MINUTE, minute)
            if (before(Calendar.getInstance())) {
                add(Calendar.DATE, NEXT_DAY)
            }
        }.timeInMillis

    fun convertAlarmDisplayTime(timeStamp: Long): String {
        val calendar = Calendar.getInstance(timeZone)
        calendar.time = Date().apply { time = timeStamp }
        val simpleDateFormat = SimpleDateFormat(FORMATTER, Locale.getDefault())
        simpleDateFormat.timeZone = timeZone
        return simpleDateFormat.format(calendar.time).plus(" ").plus(getAmPm(calendar))
    }

    private fun getAmPm(calendar: Calendar): String {
        return if (calendar.get(Calendar.AM_PM) == Calendar.AM) {
            AM
        } else {
            PM
        }
    }

}