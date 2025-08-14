package com.ordain.data.local.source

import com.ordain.data.local.JournalEntryDao
import com.ordain.data.model.JournalEntryEntity
import kotlinx.coroutines.flow.Flow

interface JournalingLocalDataSource {
    fun getAllJournalEntries(): Flow<List<JournalEntryEntity>>
    fun getJournalEntryById(id: String): Flow<JournalEntryEntity?>
    suspend fun insertJournalEntry(entry: JournalEntryEntity)
    suspend fun deleteJournalEntry(entry: JournalEntryEntity)
}

class JournalingLocalDataSourceImpl(private val journalEntryDao: JournalEntryDao) :
    JournalingLocalDataSource {
    override fun getAllJournalEntries(): Flow<List<JournalEntryEntity>> =
        journalEntryDao.getAllJournalEntries()

    override fun getJournalEntryById(id: String): Flow<JournalEntryEntity?> =
        journalEntryDao.getJournalEntryById(id)

    override suspend fun insertJournalEntry(entry: JournalEntryEntity) =
        journalEntryDao.insertJournalEntry(entry)

    override suspend fun deleteJournalEntry(entry: JournalEntryEntity) =
        journalEntryDao.deleteJournalEntry(entry)
}