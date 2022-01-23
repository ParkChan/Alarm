package com.chan.alarm.common.presentation

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.chan.alarm.common.presentation.receiver.AlarmReceiver
import com.chan.alarm.feature.domain.data.Alarm
import timber.log.Timber

object AlarmEvent {

    fun addBroadCastAlarmManager(context: Context, alarm: Alarm) {
        Timber.d("addBroadCastAlarmManager alarm >>> $alarm")
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.action = AlarmReceiver.ACTION_NAME
        val bundle = Bundle()
        bundle.putInt(AlarmReceiver.BUNDLE_KEY_ALARM_ID, alarm.id)
        intent.putExtras(bundle)

        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                alarm.id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarm.timeStamp,
            pendingIntent
        )
    }

    fun cancelBroadCastAlarmManager(context: Context, alarmId: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.action = AlarmReceiver.ACTION_NAME
        val pendingIntent =
            PendingIntent.getBroadcast(context, alarmId, intent, PendingIntent.FLAG_NO_CREATE)
        pendingIntent?.let{
            Timber.d("cancelBroadCastAlarmManager alarmId >>> $alarmId")
            alarmManager.cancel(it)
            it.cancel()
        }
    }

}