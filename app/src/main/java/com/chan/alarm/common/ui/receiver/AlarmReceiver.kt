package com.chan.alarm.common.ui.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.view.WindowManager
import androidx.core.net.toUri
import com.chan.alarm.MainActivity
import com.chan.alarm.R
import timber.log.Timber

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val action = intent?.action ?: ""
        if (action == ACTION_NAME) {
            Timber.d(">>>>>> onReceive action name $action")
            wakeLock(context)
            moveAlarmFragment(context, intent)
        }
    }

    private fun wakeLock(context: Context) {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wakeLock: PowerManager.WakeLock =
            powerManager.newWakeLock(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WAKE_LOCK_TAG
            )
        wakeLock.acquire(WAKE_LOCK_TIME_OUT)
    }

    private fun moveAlarmFragment(context: Context, intent: Intent?) {
        val bundle = intent?.extras
        val alarmId: Int? = bundle?.getInt(BUNDLE_KEY_ALARM_ID)
        Timber.d(">>>>>> onReceive $alarmId")
        alarmId?.let {
            val alarmIntent = Intent(context, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                data = context.getString(R.string.alarm_deep_link).plus(it).toUri()
            }
            context.startActivity(alarmIntent)
        }
    }

    companion object {
        const val BUNDLE_KEY_ALARM_ID = "BUNDLE_KEY_ALARM_ID"
        const val ACTION_NAME = "ADD_ALARM"
        private const val WAKE_LOCK_TIME_OUT = 5_000L
        private const val WAKE_LOCK_TAG = "MyApp:WakeLockTag"


    }
}