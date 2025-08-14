package com.ordain.domain.model

data class JournalEntry(
    val id: String,
    val title: String,
    val content: String,
    val templateId: String,
    val timestamp: Long
)