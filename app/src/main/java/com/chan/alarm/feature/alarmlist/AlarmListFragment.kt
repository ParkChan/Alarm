package com.chan.alarm.feature.alarmlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.chan.alarm.R
import com.chan.alarm.common.ui.AlarmViewModel
import com.chan.alarm.databinding.FragmentAlarmListBinding
import com.chan.alarm.feature.settings.SettingFragmentDirections
import com.chan.ui.BaseFragment

class AlarmListFragment : BaseFragment<FragmentAlarmListBinding>(
    FragmentAlarmListBinding::inflate
) {

    private val alarmViewModel by activityViewModels<AlarmViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTitle()
        initViewModel()
        initListener()
    }

    private fun initTitle() {
        binding.includeTitle.tvTitle.text = getString(R.string.title_alarm_list)
    }

    private fun initViewModel() {
        binding.alarmViewModel = alarmViewModel
    }

    private fun initListener() {
        binding.btnAddReminder.setOnClickListener {
            val action = AlarmListFragmentDirections.actionAlarmListFragmentToSettingsFragmentGraph()
            it.findNavController().navigate(action)
        }
    }

}