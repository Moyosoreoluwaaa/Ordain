package com.ordain.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ordain.data.model.PomodoroSessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PomodoroSessionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: PomodoroSessionEntity)

    @Query("SELECT * FROM pomodoro_sessions ORDER BY timestamp DESC")
    fun getAllSessions(): Flow<List<PomodoroSessionEntity>>

    @Query("SELECT * FROM pomodoro_sessions WHERE timestamp BETWEEN :startOfDay AND :endOfDay ORDER BY timestamp DESC")
    fun getSessionsForDay(startOfDay: Long, endOfDay: Long): Flow<List<PomodoroSessionEntity>>

    @Delete
    suspend fun deleteSession(session: PomodoroSessionEntity)
}