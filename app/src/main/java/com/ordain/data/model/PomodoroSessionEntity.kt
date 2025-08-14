package com.ordain.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pomodoro_sessions")
data class PomodoroSessionEntity(
    @PrimaryKey
    val id: String,
    val durationMinutes: Int,
    val timestamp: Long,
    val isCompleted: Boolean
)