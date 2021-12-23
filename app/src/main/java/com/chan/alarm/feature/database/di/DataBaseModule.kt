package com.chan.alarm.feature.database.di

import android.content.Context
import androidx.room.Room
import com.chan.alarm.feature.database.AlarmDao
import com.chan.alarm.feature.database.AlarmDatabase
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
    fun providesProductDatabase(@ApplicationContext context: Context): AlarmDatabase =
        Room.databaseBuilder(
            context,
            AlarmDatabase::class.java,
            "product.db"
        ).build()

    @Provides
    @Singleton
    fun providesProductDao(database: AlarmDatabase): AlarmDao = database.alarmDao()
}