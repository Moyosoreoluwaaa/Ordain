package com.ordain.domain.repository

import com.ordain.domain.model.Goal
import com.ordain.domain.model.Milestone
import kotlinx.coroutines.flow.Flow

interface GoalsRepository {
    fun getAllGoals(): Flow<List<Goal>>
    fun getGoalWithMilestones(goalId: String): Flow<Pair<Goal, List<Milestone>>>
    suspend fun saveGoal(goal: Goal)
    suspend fun deleteGoal(goal: Goal)
    suspend fun saveMilestone(milestone: Milestone)
    suspend fun updateMilestone(milestone: Milestone)
    suspend fun deleteMilestone(milestone: Milestone)
}