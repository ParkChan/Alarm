package com.chan.alarm.common.ui

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TimeUtilTest {

    @Test
    fun `시간과 분을 전달받아 캘린더 객체로 변환하는 테스트`() {
        val hour1 = 18
        val minute1 = 50
        val timeInMillis1 = TimeUtil.convertAlarmTimeMills(hour1, minute1)

        val hour2 = 1
        val minute2 = 50
        val timeInMillis2 = TimeUtil.convertAlarmTimeMills(hour2, minute2)

        assertEquals("18:50 PM", TimeUtil.convertAlarmDisplayTime(timeInMillis1))
        assertEquals("01:50 AM", TimeUtil.convertAlarmDisplayTime(timeInMillis2))


        println(TimeUtil.convertAlarmDisplayTime(1640331746265))
    }
}