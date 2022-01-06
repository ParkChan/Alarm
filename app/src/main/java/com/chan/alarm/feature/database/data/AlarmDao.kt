package com.chan.alarm.feature.database.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface AlarmDao : BaseDao<AlarmTable> {

    @Query("SELECT * FROM alarm")
    suspend fun select(): List<AlarmTable>

    @Query("SELECT * FROM alarm WHERE id=:id")
    suspend fun selectId(id: Int): AlarmTable

    @Query("SELECT * FROM alarm WHERE name=:alarmName")
    suspend fun selectAlarmName(alarmName: String): AlarmTable

    @Query("DELETE FROM alarm WHERE id=:id")
    suspend fun deleteId(id: Int): AlarmTable

}
