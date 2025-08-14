package com.ordain.domain.usecase

import com.ordain.domain.model.JournalEntry
import com.ordain.domain.repository.JournalingRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class SaveJournalEntry(private val repository: JournalingRepository) {
    suspend operator fun invoke(
        id: String?,
        title: String,
        content: String,
        templateId: String
    ) {
        if (title.isBlank() || content.isBlank()) {
            throw IllegalArgumentException("Title and content cannot be blank.")
        }
        val newEntry = JournalEntry(
            id = UUID.randomUUID().toString(),
            title = title,
            content = content,
            templateId = templateId,
            timestamp = System.currentTimeMillis()
        )
        repository.saveJournalEntry(newEntry)
    }
}

class GetJournalEntries(private val repository: JournalingRepository) {
    operator fun invoke(): Flow<List<JournalEntry>> {
        return repository.getAllJournalEntries()
    }
}

class DeleteJournalEntry @Inject constructor(private val repository: JournalingRepository) {
    suspend operator fun invoke(entry: JournalEntry) {
        repository.deleteJournalEntry(entry)
    }
}