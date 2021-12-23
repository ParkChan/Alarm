package com.chan.alarm.feature.database.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [AlarmTable::class],
    version = 1,
    exportSchema = false
)
abstract class AlarmDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}