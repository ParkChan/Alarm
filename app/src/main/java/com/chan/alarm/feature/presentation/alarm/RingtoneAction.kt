package com.chan.alarm.feature.presentation.alarm

import android.content.Context
import android.net.Uri

interface RingtoneAction {
    fun startRingtone(context: Context, uri: Uri)
    fun stopRingtone()
}