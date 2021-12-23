package com.chan.alarm.feature.database.data.di

import com.chan.alarm.feature.database.data.repository.AlarmRepositoryImpl
import com.chan.alarm.feature.database.data.source.DataBaseSource
import com.chan.alarm.feature.database.data.source.DataBaseSourceImpl
import com.chan.alarm.feature.database.domain.repository.AlarmRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AlarmRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsAlarmRepository(
        alarmRepositoryImpl: AlarmRepositoryImpl
    ): AlarmRepository
}