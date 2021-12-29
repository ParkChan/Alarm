package com.chan.alarm.feature.database.data.source

import com.chan.alarm.feature.database.data.AlarmDao
import com.chan.alarm.feature.database.data.AlarmTable
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DataBaseSourceImplTest {

    private val alarmDao: AlarmDao = mockk(relaxed = true)
    private lateinit var dataBaseSource: DataBaseSource

    @BeforeEach
    fun setup() {
        dataBaseSource = DataBaseSourceImpl(alarmDao)
    }

    @Test
    fun `알람정보를 저장 합니다`() = runBlocking {
        val alarmTable = AlarmTable(
            ID,
            alarmName = ALARM_NAME,
            timeStamp = TIME_STAMP,
            enableAlarm = ENABLE_ALARM,
            ringtoneUri = RINGTONE_URI
        )
        dataBaseSource.insert(alarmTable)

        coVerify {
            alarmDao.insert(alarmTable)
        }
    }

    @Test
    fun `알람정보를 조회 합니다`() = runBlocking {
        dataBaseSource.select()
        coVerify {
            alarmDao.select()
        }
    }
    companion object {
        private const val ID = 1
        private const val ALARM_NAME = "미팅"
        private const val ENABLE_ALARM = true
        private const val TIME_STAMP = 0L
        private const val RINGTONE_URI = ""
    }
}