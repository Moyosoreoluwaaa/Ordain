package com.ordain.ui.goals.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ordain.domain.model.Goal
import com.ordain.domain.model.Milestone
import com.ordain.domain.usecase.AddMilestone
import com.ordain.domain.usecase.GetGoalWithMilestones
import com.ordain.domain.usecase.UpdateMilestoneCompletion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GoalDetailUiState(
    val goal: Goal? = null,
    val milestones: List<Milestone> = emptyList(),
    val progress: Int = 0,
    val newMilestoneTitle: String = ""
)

@HiltViewModel
class GoalDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getGoalWithMilestones: GetGoalWithMilestones,
    private val addMilestone: AddMilestone,
    private val updateMilestoneCompletion: UpdateMilestoneCompletion
) : ViewModel() {

    private val _uiState = MutableStateFlow(GoalDetailUiState())
    val uiState: StateFlow<GoalDetailUiState> = _uiState.asStateFlow()

    private val goalId: String = savedStateHandle["goalId"] ?: ""

    init {
        if (goalId.isNotEmpty()) {
            viewModelScope.launch {
                getGoalWithMilestones(goalId).collectLatest { (goal, milestones) ->
                    val completedMilestones = milestones.count { it.isCompleted }
                    val totalMilestones = milestones.size
                    val progress = if (totalMilestones > 0) {
                        (completedMilestones.toFloat() / totalMilestones.toFloat() * 100).toInt()
                    } else {
                        0
                    }
                    _uiState.value = _uiState.value.copy(
                        goal = goal,
                        milestones = milestones,
                        progress = progress
                    )
                }
            }
        }
    }

    fun onNewMilestoneTitleChange(newTitle: String) {
        _uiState.value = _uiState.value.copy(newMilestoneTitle = newTitle)
    }

    fun onAddMilestone() {
        viewModelScope.launch {
            val title = uiState.value.newMilestoneTitle
            if (title.isNotBlank() && goalId.isNotEmpty()) {
                addMilestone(goalId, title)
                _uiState.value = _uiState.value.copy(newMilestoneTitle = "")
            }
        }
    }

    fun onToggleMilestoneCompletion(milestone: Milestone) {
        viewModelScope.launch {
            updateMilestoneCompletion(milestone, !milestone.isCompleted)
        }
    }
}