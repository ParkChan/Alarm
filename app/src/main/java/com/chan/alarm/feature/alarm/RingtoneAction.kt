package com.chan.alarm.feature.alarm

import android.content.Context
import android.net.Uri

interface RingtoneAction {
    fun startRingtone(context: Context, uri: Uri)
    fun stopRingtone()
}