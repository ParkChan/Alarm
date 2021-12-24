package com.chan.alarm.feature.alarm

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.chan.alarm.common.ui.AlarmViewModel
import com.chan.alarm.common.ui.TimeUtil
import com.chan.alarm.common.ui.TimeUtil.FORMAT_TYPE_HH_MM
import com.chan.alarm.databinding.FragmentAlarmBinding
import com.chan.alarm.feature.database.domain.data.Alarm
import com.chan.ui.BaseFragment
import timber.log.Timber

class AlarmFragment : BaseFragment<FragmentAlarmBinding>(
    FragmentAlarmBinding::inflate
) {

    private val alarmViewModel by activityViewModels<AlarmViewModel>()
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
        })
    }

    private fun initData() {
        val alarmId: Int? = arguments?.getInt(DEEP_LINK_ID)
        alarmId?.run {
            alarmViewModel.selectAlarmInfo(alarmId)
        }
        Timber.d(">>>>>>>>>> alarmId $alarmId")
    }


    companion object {
        private const val DEEP_LINK_ID = "alarmId"
    }
}