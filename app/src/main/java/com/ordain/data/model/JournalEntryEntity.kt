package com.ordain.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "journal_entries")
data class JournalEntryEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val content: String,
    val templateId: String,
    val timestamp: Long
)