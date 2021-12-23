package com.chan.alarm.feature.database

import com.chan.alarm.feature.database.data.AlarmTable
import com.chan.alarm.feature.database.data.repository.AlarmRepositoryImpl
import com.chan.alarm.feature.database.data.source.DataBaseSource
import com.chan.alarm.feature.database.data.source.DataBaseSourceImpl
import com.chan.alarm.feature.database.domain.repository.AlarmRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AlarmRepositoryImplTest {

    private val dataBaseSource: DataBaseSource = mockk(relaxed = true)
    private lateinit var alarmRepository: AlarmRepository

    @BeforeEach
    fun setup() {
        alarmRepository = AlarmRepositoryImpl(dataBaseSource)
    }

    @Test
    fun `Repository 를 사용하여 insert 를 수행하는 동작 테스트`() = runBlocking {

        val mockAlarmTable = AlarmTable(
            ID,
            ALARM_NAME,
            TIME_STAMP,
            IS_ALARM,
            RINGTONE_URI
        )
        alarmRepository.insert(mockAlarmTable.mapToDomain())

        coVerify {
            dataBaseSource.insert(mockAlarmTable)
        }
    }

    @Test
    fun `Repository 를 사용하여 select 를 수행하는 동작 테스트`() = runBlocking {

        alarmRepository.select()

        coVerify {
            dataBaseSource.select()
        }
    }

    companion object {
        private const val ID = 1
        private const val ALARM_NAME = "미팅"
        private const val IS_ALARM = true
        private const val TIME_STAMP = 0L
        private const val RINGTONE_URI = ""

    }
}