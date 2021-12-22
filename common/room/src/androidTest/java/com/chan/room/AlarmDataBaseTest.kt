package com.chan.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import java.util.concurrent.Executors

open class AlarmDataBaseTest {

    protected lateinit var database: AlarmDatabase

    @BeforeEach
    open fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            AlarmDatabase::class.java,
        ).setTransactionExecutor(
            Executors.newSingleThreadExecutor()
        ).build()
    }

    @AfterEach
    fun closeDb() {
        database.close()
    }

}