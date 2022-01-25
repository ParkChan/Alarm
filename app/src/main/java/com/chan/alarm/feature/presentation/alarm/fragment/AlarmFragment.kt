package com.chan.alarm.feature.presentation.alarm.fragment

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
import com.chan.alarm.common.presentation.AlarmEvent
import com.chan.alarm.common.presentation.util.TimeUtil
import com.chan.alarm.common.presentation.util.TimeUtil.FORMAT_TYPE_HH_MM
import com.chan.alarm.common.presentation.viewmodel.AlarmViewModel
import com.chan.alarm.common.presentation.vo.AlarmVo
import com.chan.alarm.databinding.FragmentAlarmBinding
import com.chan.alarm.feature.presentation.alarm.RingtoneAction
import com.chan.ui.BaseFragment
import com.chan.ui.livedata.observeEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class AlarmFragment : BaseFragment<FragmentAlarmBinding>(
    FragmentAlarmBinding::inflate
), RingtoneAction {

    private val alarmViewModel by activityViewModels<AlarmViewModel>()

    private lateinit var ringtone: Ringtone
    private lateinit var alarm: AlarmVo

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initViewModelObserve()
        initListener()
        initViewData()

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

    private fun initViewModelObserve() {
        alarmViewModel.displayAlarm.observeEvent(
            lifecycleOwner = viewLifecycleOwner, observer = {
                alarm = it
                alarm.run {
                    binding.tvRemindName.text = name
                    binding.tvRemindTime.text =
                        TimeUtil.convertAlarmDisplayTime(FORMAT_TYPE_HH_MM, timeStamp)
                    startRingtone(binding.root.context, ringtoneUri.toUri())
                }
            })
    }

    private fun initViewData() = lifecycleScope.launch {
        val alarmId: Int? = arguments?.getInt(DEEP_LINK_ID)
        Timber.d(">>>> initViewData alarmId $alarmId")
        alarmId?.run {
            alarmViewModel.selectAlarmId(alarmId)
        }
    }

    private fun dismissAlarm() = lifecycleScope.launch {
        alarmViewModel.offAlarm(alarm)
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
        stopRingtone()
        super.onDestroyView()
    }

    companion object {
        private const val DEEP_LINK_ID = "alarmId"
    }
}