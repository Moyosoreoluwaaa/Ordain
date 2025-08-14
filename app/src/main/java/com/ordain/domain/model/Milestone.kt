package com.ordain.domain.model

data class Milestone(
    val id: String,
    val goalId: String,
    val title: String,
    val isCompleted: Boolean,
    val timestamp: Long
)