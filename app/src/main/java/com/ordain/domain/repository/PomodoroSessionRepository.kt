package com.ordain.domain.repository

import com.ordain.domain.model.PomodoroSession
import kotlinx.coroutines.flow.Flow

interface PomodoroSessionRepository {
    fun getAllSessions(): Flow<List<PomodoroSession>>
    fun getSessionsForDay(startOfDay: Long, endOfDay: Long): Flow<List<PomodoroSession>>
    suspend fun saveSession(session: PomodoroSession)
}