package com.ordain.data.repository

import com.ordain.data.local.source.PomodoroSessionLocalDataSource
import com.ordain.data.model.PomodoroSessionEntity
import com.ordain.domain.model.PomodoroSession
import com.ordain.domain.repository.PomodoroSessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PomodoroSessionRepositoryImpl(private val localDataSource: PomodoroSessionLocalDataSource) : PomodoroSessionRepository {
    override fun getAllSessions(): Flow<List<PomodoroSession>> {
        return localDataSource.getAllSessions().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override fun getSessionsForDay(startOfDay: Long, endOfDay: Long): Flow<List<PomodoroSession>> {
        return localDataSource.getSessionsForDay(startOfDay, endOfDay).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun saveSession(session: PomodoroSession) {
        localDataSource.insertSession(session.toDataModel())
    }

    private fun PomodoroSessionEntity.toDomainModel(): PomodoroSession {
        return PomodoroSession(
            id = id,
            durationMinutes = durationMinutes,
            timestamp = timestamp,
            isCompleted = isCompleted
        )
    }

    private fun PomodoroSession.toDataModel(): PomodoroSessionEntity {
        return PomodoroSessionEntity(
            id = id,
            durationMinutes = durationMinutes,
            timestamp = timestamp,
            isCompleted = isCompleted
        )
    }
}