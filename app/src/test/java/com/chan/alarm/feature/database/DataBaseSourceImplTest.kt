package com.chan.alarm.feature.database

import com.chan.alarm.feature.database.source.DataBaseSource
import com.chan.alarm.feature.database.source.DataBaseSourceImpl
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
        val timeStamp = System.currentTimeMillis()
        val alarmTable = AlarmTable(
            alarmName = "회의",
            timeStamp = timeStamp,
            isAlarm = false,
            ringtoneUri = ""
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
}