package com.ordain.domain.repository

import com.ordain.domain.model.JournalEntry
import kotlinx.coroutines.flow.Flow

interface JournalingRepository {
    fun getAllJournalEntries(): Flow<List<JournalEntry>>
    fun getJournalEntryById(id: String): Flow<JournalEntry?>
    suspend fun saveJournalEntry(entry: JournalEntry)
    suspend fun deleteJournalEntry(entry: JournalEntry)
}