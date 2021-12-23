package com.chan.alarm.feature.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.chan.alarm.R
import com.chan.alarm.common.ui.AlarmViewModel
import com.chan.alarm.databinding.FragmentSettingsBinding
import com.chan.ui.BaseFragment

class SettingFragment : BaseFragment<FragmentSettingsBinding>(
    FragmentSettingsBinding::inflate
) {
    private val alarmViewModel by activityViewModels<AlarmViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTitle()
        initViewModel()
    }

    private fun initTitle() {
        binding.includeTitle.tvTitle.text = getString(R.string.title_remind_settings)
    }

    private fun initViewModel() {
        binding.alarmViewModel = alarmViewModel
    }
}

