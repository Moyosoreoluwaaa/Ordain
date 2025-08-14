package com.ordain.domain.usecase

import com.ordain.domain.model.Goal
import com.ordain.domain.model.Milestone
import com.ordain.domain.repository.GoalsRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class SaveGoal @Inject constructor(
    private val goalsRepository: GoalsRepository
) {
    suspend operator fun invoke(title: String, description: String) {
        val newGoal = Goal(
            id = UUID.randomUUID().toString(),
            title = title,
            description = description,
            timestamp = System.currentTimeMillis()
        )
        goalsRepository.saveGoal(newGoal)
    }
}

class AddGoal(private val repository: GoalsRepository) {
    suspend operator fun invoke(title: String, description: String) {
        if (title.isBlank()) {
            throw IllegalArgumentException("Goal title cannot be blank.")
        }
        val newGoal = Goal(
            id = UUID.randomUUID().toString(),
            title = title,
            description = description,
            timestamp = System.currentTimeMillis()
        )
        repository.saveGoal(newGoal)
    }
}

class GetGoals(private val repository: GoalsRepository) {
    operator fun invoke(): Flow<List<Goal>> {
        return repository.getAllGoals()
    }
}

class GetGoalWithMilestones(private val repository: GoalsRepository) {
    operator fun invoke(goalId: String): Flow<Pair<Goal, List<Milestone>>> {
        return repository.getGoalWithMilestones(goalId)
    }
}

class AddMilestone(private val repository: GoalsRepository) {
    suspend operator fun invoke(goalId: String, title: String) {
        if (title.isBlank()) {
            throw IllegalArgumentException("Milestone title cannot be blank.")
        }
        val newMilestone = Milestone(
            id = UUID.randomUUID().toString(),
            goalId = goalId,
            title = title,
            isCompleted = false,
            timestamp = System.currentTimeMillis()
        )
        repository.saveMilestone(newMilestone)
    }
}

class UpdateMilestoneCompletion(private val repository: GoalsRepository) {
    suspend operator fun invoke(milestone: Milestone, isCompleted: Boolean) {
        val updatedMilestone = milestone.copy(isCompleted = isCompleted)
        repository.updateMilestone(updatedMilestone)
    }
}

class DeleteGoal(private val repository: GoalsRepository) {
    suspend operator fun invoke(goal: Goal) {
        repository.deleteGoal(goal)
    }
}

class DeleteMilestone(private val repository: GoalsRepository) {
    suspend operator fun invoke(milestone: Milestone) {
        repository.deleteMilestone(milestone)
    }
}