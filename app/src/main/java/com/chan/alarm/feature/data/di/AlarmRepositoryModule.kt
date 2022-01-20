package com.chan.alarm.feature.data.di

import com.chan.alarm.feature.data.repository.AlarmRepositoryImpl
import com.chan.alarm.feature.domain.repository.AlarmRepository
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