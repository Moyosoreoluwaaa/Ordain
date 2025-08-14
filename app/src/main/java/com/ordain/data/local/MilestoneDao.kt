package com.ordain.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ordain.data.model.MilestoneEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MilestoneDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMilestone(milestone: MilestoneEntity)

    @Update
    suspend fun updateMilestone(milestone: MilestoneEntity)

    @Query("SELECT * FROM milestones WHERE goalId = :goalId ORDER BY timestamp ASC")
    fun getMilestonesForGoal(goalId: String): Flow<List<MilestoneEntity>>

    @Delete
    suspend fun deleteMilestone(milestone: MilestoneEntity)
}