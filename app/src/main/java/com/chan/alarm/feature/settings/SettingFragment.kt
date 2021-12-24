package com.chan.alarm.feature.settings

import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.chan.alarm.R
import com.chan.alarm.common.ui.AlarmViewModel
import com.chan.alarm.common.ui.TimeUtil
import com.chan.alarm.common.ui.TimeUtil.convertAlarmTimeMills
import com.chan.alarm.databinding.FragmentSettingsBinding
import com.chan.alarm.feature.database.domain.data.Alarm
import com.chan.ui.BaseFragment
import com.google.android.material.snackbar.Snackbar
import java.util.*


class SettingFragment : BaseFragment<FragmentSettingsBinding>(
    FragmentSettingsBinding::inflate
) {
    private val alarmViewModel by activityViewModels<AlarmViewModel>()
    private val resultLauncher: ActivityResultLauncher<Uri?> = registerForActivityResult(
        RingtoneActivityContract()
    ) {
        it?.let {
            binding.tvRingtoneSubName.text = getRingtoneTitle(it)
            alarmVo.ringtoneUri = it.toString()
        }
    }
    private val alarmVo = AlarmVo()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
        initViewModelObserve()
        initListener()
    }

    private fun initView() {
        initTitle()
        binding.tvRingtoneSubName.text = getString(R.string.ringtone_empty)

        val calendar = TimeUtil.calendar()
        binding.tpRemindTime.hour = calendar.get(Calendar.HOUR_OF_DAY)
        binding.tpRemindTime.minute = calendar.get(Calendar.MINUTE)

    }

    private fun initTitle() {
        binding.includeTitle.tvTitle.text = getString(R.string.fragment_label_remind_settings)
    }

    private fun initViewModel() {
        binding.alarmViewModel = alarmViewModel
    }

    private fun initViewModelObserve() {
        alarmViewModel.addAlarmEvent.observe(viewLifecycleOwner, {
            alarmViewModel.addBroadCastAlarmManager(binding.btnSave.context, it)

            val action = SettingFragmentDirections.actionSettingsFragmentToAlarmListFragmentGraph()
            binding.btnSave.findNavController().navigate(action)

        })
    }

    private fun initListener() {
        binding.btnSave.setOnClickListener {
            if (isValidationCheck()) {
                val remindName = binding.etRemindName.text.toString()
                val hour = binding.tpRemindTime.hour
                val minute = binding.tpRemindTime.minute

                val alarm = Alarm(
                    alarmName = remindName,
                    timeStamp = convertAlarmTimeMills(hour, minute),
                    isAlarm = true,
                    ringtoneUri = alarmVo.ringtoneUri
                )
                alarmViewModel.saveAlarm(alarm)
            }
        }

        binding.viewBgRingtone.setOnClickListener {
            resultLauncher.launch(alarmVo.getUri())
        }
    }

    private fun getRingtoneTitle(uri: Uri): String =
        RingtoneManager.getRingtone(context, uri).getTitle(context)

    private fun isValidationCheck(): Boolean {
        return when {
            binding.etRemindName.text.isEmpty() -> {
                showSnackbar(getString(R.string.snack_bar_msg_input_remind_name))
                false
            }
            alarmVo.ringtoneUri.isEmpty() -> {
                showSnackbar(getString(R.string.snack_bar_msg_select_ringtone))
                false
            }
            else -> {
                true
            }
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}

