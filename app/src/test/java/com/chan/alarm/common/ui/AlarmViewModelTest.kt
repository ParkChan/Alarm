package com.chan.alarm.common.ui

import com.chan.alarm.common.ui.viewmodel.AlarmViewModel
import com.chan.alarm.feature.database.domain.data.Alarm
import com.chan.alarm.feature.database.domain.usecase.AlarmDataBaseUseCase
import com.chan.alarm.util.CoroutinesTestExtension
import com.chan.alarm.util.InstantExecutorExtension
import com.chan.alarm.util.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class, CoroutinesTestExtension::class)
class AlarmViewModelTest {

    private val useCase: AlarmDataBaseUseCase = mockk(relaxed = true)
    private lateinit var viewModel: AlarmViewModel

    @BeforeEach
    fun setUp() {
        viewModel = AlarmViewModel(useCase)
    }

    @Test
    fun `알람을 저장하고 저장된 알람 리스트를 가져옵니다`() = runBlocking {
        val mockAlarm = Alarm(
            id = ID,
            alarmName = ALARM_NAME,
            timeStamp = TIME_STAMP,
            enableAlarm = ENABLE_ALARM,
            ringtoneUri = RINGTONE_URI
        )

        coEvery {
            useCase.insert(mockAlarm)
            useCase.select().getOrNull()
        } returns listOf(mockAlarm)

        viewModel.saveAlarm(mockAlarm)
        viewModel.selectAlarmList()

        assertEquals(
            mockAlarm,
            viewModel.alarms.getOrAwaitValue().peek()[0]
        )
    }

    companion object {
        private const val ID = 1
        private const val ALARM_NAME = "미팅"
        private const val ENABLE_ALARM = true
        private const val TIME_STAMP = 0L
        private const val RINGTONE_URI = ""
    }
}