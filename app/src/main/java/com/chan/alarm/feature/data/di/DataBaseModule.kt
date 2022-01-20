package com.chan.alarm.feature.data.di

import android.content.Context
import androidx.room.Room
import com.chan.alarm.feature.data.AlarmDao
import com.chan.alarm.feature.data.AlarmDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun providesAlarmDatabase(@ApplicationContext context: Context): AlarmDatabase =
        Room.databaseBuilder(
            context,
            AlarmDatabase::class.java,
            "alarm.db"
        ).build()

    @Provides
    @Singleton
    fun providesAlarmDao(database: AlarmDatabase): AlarmDao = database.alarmDao()
}