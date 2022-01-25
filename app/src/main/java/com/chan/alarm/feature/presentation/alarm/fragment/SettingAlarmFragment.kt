package com.chan.alarm.feature.presentation.alarm.fragment

import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.chan.alarm.R
import com.chan.alarm.common.presentation.AlarmEvent
import com.chan.alarm.common.presentation.util.SnackbarUtil
import com.chan.alarm.common.presentation.util.TimeUtil
import com.chan.alarm.common.presentation.util.TimeUtil.dayTimeInMillis
import com.chan.alarm.common.presentation.util.TimeUtil.nextDayTimeInMillis
import com.chan.alarm.common.presentation.viewmodel.AlarmViewModel
import com.chan.alarm.common.presentation.vo.AlarmVo
import com.chan.alarm.databinding.FragmentSettingAlarmBinding
import com.chan.alarm.feature.presentation.alarm.RingtoneActivityContract
import com.chan.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Calendar

@AndroidEntryPoint
class SettingAlarmFragment : BaseFragment<FragmentSettingAlarmBinding>(
    FragmentSettingAlarmBinding::inflate
) {
    private val alarmViewModel by activityViewModels<AlarmViewModel>()
    private var ringTonUri: Uri = "".toUri()
    private val resultLauncher: ActivityResultLauncher<Uri?> = registerForActivityResult(
        RingtoneActivityContract()
    ) {
        it?.let {
            binding.tvRingtoneSubName.text = getRingtoneTitle(it)
            ringTonUri = it
        }
    }
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception.message)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
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

    private fun initListener() {
        binding.btnSave.setOnClickListener {
            lifecycleScope.launch(coroutineExceptionHandler) {
                if (isValidationCheck()) {
                    val remindName = binding.etRemindName.text.toString()
                    val hour = binding.tpRemindTime.hour
                    val minute = binding.tpRemindTime.minute

                    val dayTimeInMillis = dayTimeInMillis(hour, minute)

                    val alarmVo = AlarmVo(
                        name = remindName,
                        timeStamp = if (
                            TimeUtil.isBeforeTimeInMillis(
                                Calendar.getInstance().timeInMillis,
                                dayTimeInMillis
                            )
                        ) {
                            nextDayTimeInMillis(dayTimeInMillis)
                        } else {
                            dayTimeInMillis
                        },
                        enable = true,
                        ringtoneUri = ringTonUri.toString()
                    )
                    alarmViewModel.addAlarm(alarmVo)
                    val selectedAlarm = alarmViewModel.selectAlarmName(alarmVo.name)
                    Timber.d(">>> selectedAlarm $selectedAlarm")
                    AlarmEvent.addBroadCastAlarmManager(
                        binding.btnSave.context,
                        selectedAlarm
                    )
                    binding.btnSave.findNavController().popBackStack()
                }
            }
        }
        binding.viewBgRingtone.setOnClickListener {
            resultLauncher.launch(ringTonUri)
        }
    }

    private fun getRingtoneTitle(uri: Uri): String =
        RingtoneManager.getRingtone(context, uri).getTitle(context)

    private fun isValidationCheck(): Boolean {
        return when {
            binding.etRemindName.text.isEmpty() -> {
                SnackbarUtil.show(binding.root, getString(R.string.snack_bar_msg_input_remind_name))
                false
            }
            ringTonUri == Uri.EMPTY -> {
                SnackbarUtil.show(binding.root, getString(R.string.snack_bar_msg_select_ringtone))
                false
            }
            else -> {
                true
            }
        }
    }
}

