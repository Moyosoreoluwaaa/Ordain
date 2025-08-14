package com.ordain.ui.pomodoro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ordain.domain.model.PomodoroSession
import com.ordain.domain.usecase.GetDailyPomodoroSessions
import com.ordain.domain.usecase.SavePomodoroSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PomodoroUiState(
    val totalTimeInMinutes: Int = 5,
    val timeLeftInSeconds: Int = 5 * 60,
    val isRunning: Boolean = false,
    val sessionsToday: List<PomodoroSession> = emptyList()
)

@HiltViewModel
class PomodoroViewModel @Inject constructor(
    private val savePomodoroSession: SavePomodoroSession,
    private val getDailyPomodoroSessions: GetDailyPomodoroSessions
) : ViewModel() {

    private val _uiState = MutableStateFlow(PomodoroUiState())
    val uiState: StateFlow<PomodoroUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    init {
        viewModelScope.launch {
            getDailyPomodoroSessions().collect { sessions ->
                _uiState.update { it.copy(sessionsToday = sessions) }
            }
        }
    }

    fun onStartPauseClicked() {
        if (_uiState.value.isRunning) {
            pauseTimer()
        } else {
            startTimer()
        }
    }

    fun onResetClicked() {
        resetTimer()
    }

    private fun startTimer() {
        _uiState.update { it.copy(isRunning = true) }
        timerJob = viewModelScope.launch {
            while (isActive && _uiState.value.timeLeftInSeconds > 0) {
                delay(1000L)
                _uiState.update { it.copy(timeLeftInSeconds = it.timeLeftInSeconds - 1) }
            }
            if (_uiState.value.timeLeftInSeconds <= 0) {
                onTimerFinished()
            }
        }
    }

    private fun pauseTimer() {
        timerJob?.cancel()
        _uiState.update { it.copy(isRunning = false) }
    }

    private fun resetTimer() {
        timerJob?.cancel()
        _uiState.update { it.copy(
            isRunning = false,
            timeLeftInSeconds = it.totalTimeInMinutes * 60
        ) }
    }

    private fun onTimerFinished() {
        viewModelScope.launch {
            val session = uiState.value
            savePomodoroSession(session.totalTimeInMinutes, isCompleted = true)
        }
        resetTimer()
    }
}