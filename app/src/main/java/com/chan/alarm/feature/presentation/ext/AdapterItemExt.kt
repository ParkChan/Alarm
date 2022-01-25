package com.chan.alarm.feature.presentation.ext

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.chan.alarm.common.presentation.util.TimeUtil
import com.chan.alarm.common.presentation.util.TimeUtil.FORMAT_TYPE_HH_MM_AA
import com.chan.alarm.common.presentation.vo.AlarmVo

@BindingAdapter("setTime")
fun bindingTime(textView: TextView, alarmVo: AlarmVo) {
    textView.text = TimeUtil.convertAlarmDisplayTime(FORMAT_TYPE_HH_MM_AA, alarmVo.timeStamp)
}