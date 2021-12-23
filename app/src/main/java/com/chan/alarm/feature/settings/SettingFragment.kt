package com.chan.alarm.feature.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.chan.alarm.R
import com.chan.alarm.common.ui.AlarmViewModel
import com.chan.alarm.databinding.FragmentSettingsBinding
import com.chan.alarm.feature.database.domain.data.Alarm
import com.chan.alarm.common.ui.TimeUtil.convertAlarmTimeMills
import com.chan.ui.BaseFragment
import timber.log.Timber

class SettingFragment : BaseFragment<FragmentSettingsBinding>(
    FragmentSettingsBinding::inflate
) {
    private val alarmViewModel by activityViewModels<AlarmViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTitle()
        initViewModel()
        initListener()
    }

    private fun initTitle() {
        binding.includeTitle.tvTitle.text = getString(R.string.title_remind_settings)
    }

    private fun initViewModel() {
        binding.alarmViewModel = alarmViewModel
    }

    private fun initListener() {
        binding.btnSave.setOnClickListener {
            val remindName = binding.etRemindName.text.toString()
            val hour = binding.tpRemindTime.hour
            val minute = binding.tpRemindTime.minute
            
            val timeInMillis = convertAlarmTimeMills(hour, minute)
            val ringtone = ""

            alarmViewModel.saveAlarm(
                Alarm(
                    alarmName = remindName,
                    timeStamp = timeInMillis,
                    isAlarm = true,
                    ringtoneUri = ringtone,
                )
            )
            binding.btnSave.findNavController().popBackStack()
        }
    }
}

