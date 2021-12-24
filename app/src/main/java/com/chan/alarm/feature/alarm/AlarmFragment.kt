package com.chan.alarm.feature.alarm

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.chan.alarm.common.ui.AlarmViewModel
import com.chan.alarm.common.ui.TimeUtil
import com.chan.alarm.common.ui.TimeUtil.FORMAT_TYPE_HH_MM
import com.chan.alarm.databinding.FragmentAlarmBinding
import com.chan.alarm.feature.database.domain.data.Alarm
import com.chan.ui.BaseFragment

class AlarmFragment : BaseFragment<FragmentAlarmBinding>(
    FragmentAlarmBinding::inflate
), RingtoneAction {

    private val alarmViewModel by activityViewModels<AlarmViewModel>()

    private lateinit var ringtoneManager: RingtoneManager
    private lateinit var ringtone: Ringtone
    private lateinit var alarm: Alarm

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initViewModelObserve()
        initListener()
        initData()
    }

    private fun initListener() {

        binding.btnClose.setOnClickListener {
            alarmViewModel.updateAlarm(alarm.apply { isAlarm = false })
            val action = AlarmFragmentDirections.actionAlarmFragmentToAlarmListFragmentGraph()
            binding.btnClose.findNavController().navigate(action)
            alarmViewModel.cancelBroadCastAlarmManager(binding.btnClose.context, alarm.id)
            stopRingtone()
        }
    }

    private fun initViewModel() {
        binding.alarmViewModel = alarmViewModel
    }

    private fun initViewModelObserve() {
        alarmViewModel.wakeAlarm.observe(viewLifecycleOwner, {
            alarm = it
            binding.tvRemindName.text = it.alarmName
            binding.tvRemindTime.text =
                TimeUtil.convertAlarmDisplayTime(FORMAT_TYPE_HH_MM, it.timeStamp)
            startRingtone(binding.root.context, it.ringtoneUri.toUri())
        })
    }

    private fun initData() {
        val alarmId: Int? = arguments?.getInt(DEEP_LINK_ID)
        alarmId?.run {
            alarmViewModel.selectAlarmInfo(alarmId)
        }
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