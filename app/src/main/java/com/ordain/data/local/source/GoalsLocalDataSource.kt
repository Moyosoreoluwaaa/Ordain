package com.ordain.data.local.source

import com.ordain.data.local.GoalDao
import com.ordain.data.local.MilestoneDao
import com.ordain.data.model.GoalEntity
import com.ordain.data.model.MilestoneEntity
import kotlinx.coroutines.flow.Flow

interface GoalsLocalDataSource {
    fun getAllGoals(): Flow<List<GoalEntity>>
    fun getGoalById(id: String): Flow<GoalEntity>
    suspend fun insertGoal(goal: GoalEntity)
    suspend fun deleteGoal(goal: GoalEntity)
    fun getMilestonesForGoal(goalId: String): Flow<List<MilestoneEntity>>
    suspend fun insertMilestone(milestone: MilestoneEntity)
    suspend fun updateMilestone(milestone: MilestoneEntity)
    suspend fun deleteMilestone(milestone: MilestoneEntity)
}

class GoalsLocalDataSourceImpl(
    private val goalDao: GoalDao,
    private val milestoneDao: MilestoneDao
) : GoalsLocalDataSource {

    override fun getAllGoals(): Flow<List<GoalEntity>> = goalDao.getAllGoals()
    override fun getGoalById(id: String): Flow<GoalEntity> = goalDao.getGoalById(id)
    override suspend fun insertGoal(goal: GoalEntity) = goalDao.insertGoal(goal)
    override suspend fun deleteGoal(goal: GoalEntity) = goalDao.deleteGoal(goal)
    override fun getMilestonesForGoal(goalId: String): Flow<List<MilestoneEntity>> = milestoneDao.getMilestonesForGoal(goalId)
    override suspend fun insertMilestone(milestone: MilestoneEntity) = milestoneDao.insertMilestone(milestone)
    override suspend fun updateMilestone(milestone: MilestoneEntity) = milestoneDao.updateMilestone(milestone)
    override suspend fun deleteMilestone(milestone: MilestoneEntity) = milestoneDao.deleteMilestone(milestone)
}