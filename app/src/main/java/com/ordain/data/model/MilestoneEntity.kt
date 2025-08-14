package com.ordain.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "milestones",
    foreignKeys = [ForeignKey(
        entity = GoalEntity::class,
        parentColumns = ["id"],
        childColumns = ["goalId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["goalId"])]
)
data class MilestoneEntity(
    @PrimaryKey
    val id: String,
    val goalId: String,
    val title: String,
    val isCompleted: Boolean,
    val timestamp: Long
)