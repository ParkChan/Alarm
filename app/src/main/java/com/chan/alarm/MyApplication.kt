package com.chan.alarm

import android.app.Application
import androidx.startup.AppInitializer
import com.chan.alarm.common.startup.TimberInitializer
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.d("MyApplication onCreate()")
    }
}