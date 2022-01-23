package com.chan.alarm.common.presentation

import com.chan.alarm.common.presentation.util.TimeUtil
import com.chan.alarm.common.presentation.util.TimeUtil.FORMAT_TYPE_HH_MM_AA
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TimeUtilTest {

    private val oneDayTimeInMillis = 24 * 60 * 60 * 1000
    private val hour = 18
    private val minute = 50
    private val timeInMillis = TimeUtil.dayTimeInMillis(hour, minute)

    @Test
    fun `사용자 UI FORMAT 으로 변환하는 테스트`() {
        assertEquals(
            "18:50 오후",
            TimeUtil.convertAlarmDisplayTime(FORMAT_TYPE_HH_MM_AA, timeInMillis)
        )
    }

    @Test
    fun `현재 시간에 하루를 더하는 기능 테스트`() {
        assertEquals(
            timeInMillis + oneDayTimeInMillis,
            TimeUtil.nextDayTimeInMillis(timeInMillis)
        )
    }

    @Test
    fun `이전 timeInMillis 인지 비교하는 테스트`() {

        val alarmHour = 17
        val alarmMinute = 50

        assertEquals(
            true,
            TimeUtil.isBeforeTimeInMillis(
                defaultTimeInMillis = timeInMillis,
                userTimeInMillis = TimeUtil.dayTimeInMillis(alarmHour, alarmMinute)
            )
        )
    }

    companion object {
        private const val FORMAT_TYPE_YYYY_MM_DD_HH_MM_AA = "MM-dd HH:mm aa"
    }
}