package com.chan.alarm.feature.domain.di

import com.chan.alarm.feature.domain.usecase.AlarmDataBaseUseCase
import com.chan.alarm.feature.domain.usecase.AlarmDataBaseUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AlarmUseCaseModule {
    @Binds
    @Singleton
    abstract fun bindsAlarmUseCaseModule(useCase: AlarmDataBaseUseCaseImpl): AlarmDataBaseUseCase
}