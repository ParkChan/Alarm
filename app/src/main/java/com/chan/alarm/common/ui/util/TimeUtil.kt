package com.chan.alarm.common.ui.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.TimeZone
import java.util.Date
import java.util.Locale

object TimeUtil {

    private const val NEXT_DAY = 1
    const val FORMAT_TYPE_HH_MM_AA = "HH:mm aa"
    const val FORMAT_TYPE_HH_MM = "HH:mm"
    private val timeZone = TimeZone.getDefault()

    fun dayTimeInMillis(hour: Int, minute: Int): Long {
        return Calendar.getInstance(timeZone).apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }.timeInMillis
    }

    fun isBeforeTimeInMillis(defaultTimeInMillis: Long, userTimeInMillis: Long): Boolean =
        defaultTimeInMillis > userTimeInMillis

    fun nextDayTimeInMillis(timeStamp: Long): Long =
        Calendar.getInstance(timeZone).apply {
            timeInMillis = timeStamp
            add(Calendar.DATE, NEXT_DAY)
        }.timeInMillis

    fun calendar(): Calendar = Calendar.getInstance(timeZone)

    fun convertAlarmDisplayTime(format: String, timeStamp: Long): String {
        val date = Date().apply {
            time = timeStamp
        }
        val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
        return simpleDateFormat.format(date)
    }

}