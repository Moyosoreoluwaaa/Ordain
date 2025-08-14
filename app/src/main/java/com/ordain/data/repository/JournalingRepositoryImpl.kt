package com.ordain.data.repository

import com.ordain.data.local.source.JournalingLocalDataSource
import com.ordain.data.model.JournalEntryEntity
import com.ordain.domain.model.JournalEntry
import com.ordain.domain.repository.JournalingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class JournalingRepositoryImpl @Inject constructor(
    private val localDataSource: JournalingLocalDataSource
) : JournalingRepository {

    override fun getAllJournalEntries(): Flow<List<JournalEntry>> {
        return localDataSource.getAllJournalEntries().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override fun getJournalEntryById(id: String): Flow<JournalEntry?> {
        return localDataSource.getJournalEntryById(id).map { it?.toDomainModel() }
    }

    override suspend fun saveJournalEntry(entry: JournalEntry) {
        val entryExists = localDataSource.getJournalEntryById(entry.id).first() != null
        if (entryExists) {
            localDataSource.insertJournalEntry(entry.toDataModel()) // This handles both insert and update
        } else {
            localDataSource.insertJournalEntry(entry.toDataModel())
        }
    }

    override suspend fun deleteJournalEntry(entry: JournalEntry) {
        localDataSource.deleteJournalEntry(entry.toDataModel())
    }

    private fun JournalEntryEntity.toDomainModel(): JournalEntry {
        return JournalEntry(
            id = id,
            title = title,
            content = content,
            templateId = templateId,
            timestamp = timestamp
        )
    }

    private fun JournalEntry.toDataModel(): JournalEntryEntity {
        return JournalEntryEntity(
            id = id,
            title = title,
            content = content,
            templateId = templateId,
            timestamp = timestamp
        )
    }
}


//class JournalingRepositoryImpl(private val localDataSource: JournalingLocalDataSource) : JournalingRepository {
//
//    override fun getAllJournalEntries(): Flow<List<JournalEntry>> {
//        return localDataSource.getAllJournalEntries().map { entities ->
//            entities.map { it.toDomainModel() }
//        }
//    }
//
//    override fun getJournalEntryById(id: String): Flow<JournalEntry> {
//        return localDataSource.getJournalEntryById(id).map { it.toDomainModel() }
//    }
//
//    override suspend fun saveJournalEntry(entry: JournalEntry) {
//        localDataSource.insertJournalEntry(entry.toDataModel())
//    }
//
//    override suspend fun deleteJournalEntry(entry: JournalEntry) {
//        localDataSource.deleteJournalEntry(entry.toDataModel())
//    }
//
//    private fun JournalEntryEntity.toDomainModel(): JournalEntry {
//        return JournalEntry(
//            id = id,
//            title = title,
//            content = content,
//            templateId = templateId,
//            timestamp = timestamp
//        )
//    }
//
//    private fun JournalEntry.toDataModel(): JournalEntryEntity {
//        return JournalEntryEntity(
//            id = id,
//            title = title,
//            content = content,
//            templateId = templateId,
//            timestamp = timestamp
//        )
//    }
//}
