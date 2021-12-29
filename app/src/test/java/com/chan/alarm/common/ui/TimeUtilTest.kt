package com.chan.alarm.common.ui

import com.chan.alarm.common.ui.util.TimeUtil
import com.chan.alarm.common.ui.util.TimeUtil.FORMAT_TYPE_HH_MM_AA
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TimeUtilTest {

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
    fun `현재 날짜를 확인하고 하루를 더하는 기능 테스트`() {
        assertEquals(
            "2021-12-29 18:50 오후",
            TimeUtil.convertAlarmDisplayTime(FORMAT_TYPE_YYYY_MM_DD_HH_MM_AA, timeInMillis)
        )

        assertEquals(
            "2021-12-30 18:50 오후",
            TimeUtil.convertAlarmDisplayTime(
                FORMAT_TYPE_YYYY_MM_DD_HH_MM_AA,
                TimeUtil.nextDayTimeInMillis(timeInMillis)
            )
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
        private const val FORMAT_TYPE_YYYY_MM_DD_HH_MM_AA = "yyyy-MM-dd HH:mm aa"
    }
}