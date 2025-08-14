package com.ordain.domain.usecase

import com.ordain.domain.model.JournalEntry
import com.ordain.domain.repository.JournalingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetJournalEntry @Inject constructor(
    private val journalingRepository: JournalingRepository
) {
    operator fun invoke(entryId: String): Flow<JournalEntry?> {
        return journalingRepository.getJournalEntryById(entryId)
    }
}
class GetJournalEntries @Inject constructor(
    private val journalingRepository: JournalingRepository
) {
    operator fun invoke(): Flow<List<JournalEntry>> {
        return journalingRepository.getAllJournalEntries()
    }
}