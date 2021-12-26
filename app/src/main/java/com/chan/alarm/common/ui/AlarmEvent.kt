package com.chan.alarm.common.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.chan.alarm.feature.database.domain.data.Alarm

object AlarmEvent {

    fun addBroadCastAlarmManager(context: Context, alarm: Alarm) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
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
        val pendingIntent =
            PendingIntent.getBroadcast(context, alarmId, intent, PendingIntent.FLAG_NO_CREATE)

        pendingIntent?.let{
            alarmManager.cancel(pendingIntent)
        }
    }

}