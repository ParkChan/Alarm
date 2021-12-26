package com.chan.alarm.feature.alarm

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.chan.alarm.common.ui.AlarmEvent
import com.chan.alarm.common.ui.AlarmViewModel
import com.chan.alarm.common.ui.TimeUtil
import com.chan.alarm.common.ui.TimeUtil.FORMAT_TYPE_HH_MM
import com.chan.alarm.databinding.FragmentAlarmBinding
import com.chan.alarm.feature.database.domain.data.Alarm
import com.chan.alarm.feature.database.domain.usecase.AlarmDataBaseUseCase
import com.chan.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmFragment : BaseFragment<FragmentAlarmBinding>(
    FragmentAlarmBinding::inflate
), RingtoneAction {

    private val alarmViewModel by activityViewModels<AlarmViewModel>()

    @Inject
    lateinit var alarmDataBaseUseCase: AlarmDataBaseUseCase

    private lateinit var ringtone: Ringtone
    private lateinit var alarm: Alarm

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewData()
        initViewModel()
        initListener()

    }

    private fun initListener() {
        binding.btnDismiss.setOnClickListener {
            lifecycleScope.launch {
                dismissAlarm()
                alarmViewModel.selectAlarmList()
            }
        }
    }

    private fun initViewModel() {
        binding.alarmViewModel = alarmViewModel
    }

    private fun initViewData() = lifecycleScope.launch {
        val alarmId: Int? = arguments?.getInt(DEEP_LINK_ID)
        alarmId?.run {
            alarm = alarmDataBaseUseCase.selectId(alarmId).getOrNull() ?: Alarm()
            alarm.run {
                binding.tvRemindName.text = alarmName
                binding.tvRemindTime.text =
                    TimeUtil.convertAlarmDisplayTime(FORMAT_TYPE_HH_MM, timeStamp)
                startRingtone(binding.root.context, ringtoneUri.toUri())
            }
        }
    }

    private fun dismissAlarm() = lifecycleScope.launch {
        alarmDataBaseUseCase.update(alarm.apply { isAlarm = false })
        val action = AlarmFragmentDirections.actionAlarmFragmentToAlarmListFragmentGraph()
        binding.btnDismiss.findNavController().navigate(action)
        AlarmEvent.cancelBroadCastAlarmManager(binding.btnDismiss.context, alarm.id)
    }

    override fun startRingtone(context: Context, uri: Uri) {
        ringtone = RingtoneManager.getRingtone(context, uri)
        ringtone.play()
    }

    override fun stopRingtone() {
        ringtone.run {
            if (ringtone.isPlaying) {
                ringtone.stop()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopRingtone()
    }

    companion object {
        private const val DEEP_LINK_ID = "alarmId"
    }
}