package com.chan.alarm.feature.presentation.alarmlist.ext

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.chan.alarm.common.presentation.util.TimeUtil
import com.chan.alarm.common.presentation.util.TimeUtil.FORMAT_TYPE_HH_MM_AA
import com.chan.alarm.feature.domain.data.Alarm


@BindingAdapter("setTime")
fun bindingTime(textView: TextView, alarm: Alarm) {
    textView.text = TimeUtil.convertAlarmDisplayTime(FORMAT_TYPE_HH_MM_AA, alarm.timeStamp)
}