package com.chan.alarm.feature.database.di

import com.chan.alarm.feature.database.source.DataBaseSource
import com.chan.alarm.feature.database.source.DataBaseSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataBaseSourceModule {
    @Binds
    @Singleton
    abstract fun bindsDataBaseSource(
        productLocalDataSourceImpl: DataBaseSourceImpl
    ): DataBaseSource
}