package com.ordain.domain.usecase

import com.ordain.domain.model.PomodoroSession
import com.ordain.domain.repository.PomodoroSessionRepository
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import java.util.UUID

class SavePomodoroSession(private val repository: PomodoroSessionRepository) {
    suspend operator fun invoke(durationMinutes: Int, isCompleted: Boolean) {
        val newSession = PomodoroSession(
            id = UUID.randomUUID().toString(),
            durationMinutes = durationMinutes,
            timestamp = System.currentTimeMillis(),
            isCompleted = isCompleted
        )
        repository.saveSession(newSession)
    }
}

class GetPomodoroSessions(private val repository: PomodoroSessionRepository) {
    operator fun invoke(): Flow<List<PomodoroSession>> {
        return repository.getAllSessions()
    }
}

class GetDailyPomodoroSessions(private val repository: PomodoroSessionRepository) {
    operator fun invoke(): Flow<List<PomodoroSession>> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfDay = calendar.timeInMillis
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val endOfDay = calendar.timeInMillis - 1
        return repository.getSessionsForDay(startOfDay, endOfDay)
    }
}