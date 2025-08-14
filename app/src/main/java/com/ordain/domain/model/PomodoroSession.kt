package com.ordain.domain.model

data class PomodoroSession(
    val id: String,
    val durationMinutes: Int,
    val timestamp: Long,
    val isCompleted: Boolean
)