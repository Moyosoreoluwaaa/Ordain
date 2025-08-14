package com.ordain.data.local.source

import com.ordain.data.local.PomodoroSessionDao
import com.ordain.data.model.PomodoroSessionEntity
import kotlinx.coroutines.flow.Flow

interface PomodoroSessionLocalDataSource {
    fun getAllSessions(): Flow<List<PomodoroSessionEntity>>
    fun getSessionsForDay(startOfDay: Long, endOfDay: Long): Flow<List<PomodoroSessionEntity>>
    suspend fun insertSession(session: PomodoroSessionEntity)
}

class PomodoroSessionLocalDataSourceImpl(private val pomodoroSessionDao: PomodoroSessionDao) : PomodoroSessionLocalDataSource {
    override fun getAllSessions(): Flow<List<PomodoroSessionEntity>> = pomodoroSessionDao.getAllSessions()
    override fun getSessionsForDay(startOfDay: Long, endOfDay: Long): Flow<List<PomodoroSessionEntity>> = pomodoroSessionDao.getSessionsForDay(startOfDay, endOfDay)
    override suspend fun insertSession(session: PomodoroSessionEntity) = pomodoroSessionDao.insertSession(session)
}