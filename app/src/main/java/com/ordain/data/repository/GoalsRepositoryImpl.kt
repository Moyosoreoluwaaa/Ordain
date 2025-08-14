package com.ordain.data.repository

import com.ordain.data.local.source.GoalsLocalDataSource
import com.ordain.data.model.GoalEntity
import com.ordain.data.model.MilestoneEntity
import com.ordain.domain.model.Goal
import com.ordain.domain.model.Milestone
import com.ordain.domain.repository.GoalsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class GoalsRepositoryImpl(
    private val localDataSource: GoalsLocalDataSource
) : GoalsRepository {

    override fun getAllGoals(): Flow<List<Goal>> {
        return localDataSource.getAllGoals().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override fun getGoalWithMilestones(goalId: String): Flow<Pair<Goal, List<Milestone>>> {
        val goalFlow = localDataSource.getGoalById(goalId).map { it.toDomainModel() }
        val milestonesFlow = localDataSource.getMilestonesForGoal(goalId).map { milestoneEntities ->
            milestoneEntities.map { it.toDomainModel() }
        }
        return combine(goalFlow, milestonesFlow) { goal, milestones ->
            Pair(goal, milestones)
        }
    }

    override suspend fun saveGoal(goal: Goal) {
        localDataSource.insertGoal(goal.toDataModel())
    }

    override suspend fun deleteGoal(goal: Goal) {
        localDataSource.deleteGoal(goal.toDataModel())
    }

    override suspend fun saveMilestone(milestone: Milestone) {
        localDataSource.insertMilestone(milestone.toDataModel())
    }

    override suspend fun updateMilestone(milestone: Milestone) {
        localDataSource.updateMilestone(milestone.toDataModel())
    }

    override suspend fun deleteMilestone(milestone: Milestone) {
        localDataSource.deleteMilestone(milestone.toDataModel())
    }

    private fun GoalEntity.toDomainModel(): Goal {
        return Goal(
            id = id,
            title = title,
            description = description,
            timestamp = timestamp
        )
    }

    private fun MilestoneEntity.toDomainModel(): Milestone {
        return Milestone(
            id = id,
            goalId = goalId,
            title = title,
            isCompleted = isCompleted,
            timestamp = timestamp
        )
    }

    private fun Goal.toDataModel(): GoalEntity {
        return GoalEntity(
            id = id,
            title = title,
            description = description,
            timestamp = timestamp
        )
    }

    private fun Milestone.toDataModel(): MilestoneEntity {
        return MilestoneEntity(
            id = id,
            goalId = goalId,
            title = title,
            isCompleted = isCompleted,
            timestamp = timestamp
        )
    }
}