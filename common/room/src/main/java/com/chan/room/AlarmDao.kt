package com.chan.room

import androidx.room.Dao
import androidx.room.Query

@Dao
interface AlarmDao : BaseDao<AlarmTable> {

    @Query("SELECT * FROM alarm")
    suspend fun select(): List<AlarmTable>

}
