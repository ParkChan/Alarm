package com.chan.alarm.common.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.chan.alarm.MainActivity
import com.chan.alarm.R
import timber.log.Timber

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        Timber.d(">>>>>> onReceive ${intent?.extras}")
        val bundle = intent?.extras
        val alarmId: Int? = bundle?.getInt(BUNDLE_KEY_ALARM_ID)
        Timber.d(">>>>>> alarmId $alarmId")
        alarmId?.let {
            moveAlarmFragment(context, it)
        }
    }

    private fun moveAlarmFragment(context: Context, id: Int) {
        val deepLinkIntent = Intent(context, MainActivity::class.java)
        deepLinkIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        deepLinkIntent.data =
            context.getString(R.string.alarm_deep_link).plus(id).toUri()
        context.startActivity(deepLinkIntent)
    }

    companion object {
        const val BUNDLE_KEY_ALARM_ID = "BUNDLE_KEY_ALARM_ID"

    }
}