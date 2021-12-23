package com.chan.alarm.feature.alarmlist.ext

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.chan.alarm.common.ui.TimeUtil
import com.chan.alarm.feature.database.domain.data.Alarm


@BindingAdapter("setTime")
fun bindingTime(textView: TextView, alarm: Alarm) {
    textView.text = TimeUtil.convertAlarmDisplayTime(alarm.timeStamp)
}
