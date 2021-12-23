package com.chan.room

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AlarmDaoTest : AlarmDataBaseTest() {

    private lateinit var alarmDao: AlarmDao

    @BeforeEach
    override fun setup() {
        super.setup()
        alarmDao = database.alarmDao()
    }

    @Test
    fun writeAlarmAndRead() = runBlocking {
        val timeStamp = System.currentTimeMillis()
        val alarmTable = AlarmTable(
            alarmName = "회의",
            timeStamp = timeStamp,
            isAlarm = false,
            ringtoneUri = ""
        )
        alarmDao.insert(alarmTable)
        val alarm = alarmDao.select()

        assertEquals(ALARM_ID, alarm[0].id)
        assertEquals(ALARM_NAME, alarm[0].alarmName)
        assertEquals(timeStamp, alarm[0].timeStamp)
        assertEquals(IS_ALARM, alarm[0].isAlarm)
        assertEquals(RINGTONE_URI, alarm[0].ringtoneUri)
    }

    companion object {
        private const val ALARM_ID = 1
        private const val ALARM_NAME = "회의"
        private const val IS_ALARM = false
        private const val RINGTONE_URI = ""
    }
}