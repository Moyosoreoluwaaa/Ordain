package com.ordain.ui.goals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ordain.domain.model.Goal
import com.ordain.domain.usecase.DeleteGoal
import com.ordain.domain.usecase.GetGoals
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GoalsUiState(
    val goals: List<Goal> = emptyList()
)

@HiltViewModel
class GoalsViewModel @Inject constructor(
    private val getGoals: GetGoals,
    private val deleteGoal: DeleteGoal
) : ViewModel() {

    private val _uiState = MutableStateFlow(GoalsUiState())
    val uiState: StateFlow<GoalsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getGoals().collect { goals ->
                _uiState.value = _uiState.value.copy(goals = goals)
            }
        }
    }

    fun onDeleteGoal(goal: Goal) {
        viewModelScope.launch {
            deleteGoal(goal)
        }
    }
}

