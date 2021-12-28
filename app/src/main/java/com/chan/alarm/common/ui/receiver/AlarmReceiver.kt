package com.chan.alarm.common.ui.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.chan.alarm.MainActivity
import com.chan.alarm.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val bundle = intent?.extras
        val alarmId: Int? = bundle?.getInt(BUNDLE_KEY_ALARM_ID)
        alarmId?.let {
            moveAlarmFragment(context, it)
        }
    }

    private fun moveAlarmFragment(context: Context, id: Int) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.data =
            context.getString(R.string.alarm_deep_link).plus(id).toUri()
        context.startActivity(intent)
    }

    companion object {
        const val BUNDLE_KEY_ALARM_ID = "BUNDLE_KEY_ALARM_ID"

    }
}