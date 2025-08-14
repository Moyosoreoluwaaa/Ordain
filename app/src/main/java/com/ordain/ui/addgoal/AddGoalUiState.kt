package com.ordain.ui.addgoal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ordain.domain.usecase.SaveGoal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddGoalUiState(
    val title: String = "",
    val description: String = ""
)

@HiltViewModel
class AddGoalViewModel @Inject constructor(
    private val saveGoal: SaveGoal
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddGoalUiState())
    val uiState: StateFlow<AddGoalUiState> = _uiState.asStateFlow()

    fun onTitleChange(newTitle: String) {
        _uiState.update { it.copy(title = newTitle) }
    }

    fun onDescriptionChange(newDescription: String) {
        _uiState.update { it.copy(description = newDescription) }
    }

    fun onSaveGoal(onSuccess: () -> Unit) {
        val currentState = uiState.value
        if (currentState.title.isNotBlank()) {
            viewModelScope.launch {
                saveGoal(currentState.title, currentState.description)
                onSuccess()
            }
        }
    }
}