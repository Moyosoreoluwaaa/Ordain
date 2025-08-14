package com.ordain.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val isCompleted: Boolean = false,
    val timestamp: Long
)