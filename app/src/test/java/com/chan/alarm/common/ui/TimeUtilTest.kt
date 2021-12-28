package com.chan.alarm.common.ui

import com.chan.alarm.common.ui.util.TimeUtil
import com.chan.alarm.common.ui.util.TimeUtil.FORMAT_TYPE_HH_MM_AA
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

        assertEquals("18:50 오후", TimeUtil.convertAlarmDisplayTime(FORMAT_TYPE_HH_MM_AA, timeInMillis1))
        assertEquals("01:50 오전", TimeUtil.convertAlarmDisplayTime(FORMAT_TYPE_HH_MM_AA, timeInMillis2))
    }
}