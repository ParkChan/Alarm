package com.chan.alarm.feature.settings

import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.chan.alarm.R
import com.chan.alarm.common.ui.AlarmEvent
import com.chan.alarm.common.ui.util.SnackbarUtil
import com.chan.alarm.common.ui.util.TimeUtil
import com.chan.alarm.common.ui.util.TimeUtil.dayTimeInMillis
import com.chan.alarm.common.ui.util.TimeUtil.nextDayTimeInMillis
import com.chan.alarm.common.ui.viewmodel.AlarmViewModel
import com.chan.alarm.databinding.FragmentSettingsBinding
import com.chan.alarm.feature.database.domain.data.Alarm
import com.chan.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Calendar

@AndroidEntryPoint
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
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception.message)
    }
    private val alarmVo = AlarmVo()

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

                    val alarm = Alarm(
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
                        ringtoneUri = alarmVo.ringtoneUri
                    )
                    alarmViewModel.addAlarm(alarm)
                    val selectedAlarm = alarmViewModel.selectAlarmName(alarm.name)
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
            resultLauncher.launch(alarmVo.getUri())
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
            alarmVo.ringtoneUri.isEmpty() -> {
                SnackbarUtil.show(binding.root, getString(R.string.snack_bar_msg_select_ringtone))
                false
            }
            else -> {
                true
            }
        }
    }
}

