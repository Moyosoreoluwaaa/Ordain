package com.ordain.ui.journaling.templates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ordain.domain.model.JournalEntry
import com.ordain.domain.usecase.GetJournalEntries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class JournalTemplate(
    val id: String,
    val name: String,
    val description: String,
    val contentPrompt: String
)
data class JournalTemplatesUiState(
    val templates: List<JournalTemplate> = emptyList(),
    val recentEntries: List<JournalEntry> = emptyList()
)

@HiltViewModel
class JournalTemplatesViewModel @Inject constructor(
    private val getJournalEntries: GetJournalEntries
) : ViewModel() {

    private val _uiState = MutableStateFlow(JournalTemplatesUiState())
    val uiState: StateFlow<JournalTemplatesUiState> = _uiState.asStateFlow()

    private val allTemplates = listOf(
        JournalTemplate(
            id = "daily_reflection",
            name = "Daily Reflection",
            description = "A simple check-in to reflect on your day.",
            contentPrompt = "What was the highlight of your day? What's one thing you're grateful for?"
        ),
        JournalTemplate(
            id = "gratitude",
            name = "Gratitude",
            description = "Focus on the positive things in your life.",
            contentPrompt = "List 3 things you are grateful for today and why."
        ),
        JournalTemplate(
            id = "freestyle",
            name = "Freestyle",
            description = "Write whatever is on your mind.",
            contentPrompt = "Start writing..."
        )
    )

    init {
        _uiState.update { it.copy(templates = allTemplates) }
        viewModelScope.launch {
            getJournalEntries().collect { entries ->
                _uiState.update { it.copy(recentEntries = entries) }
            }
        }
    }
}
//
//data class JournalTemplatesUiState(
//    val templates: List<JournalTemplate> = emptyList()
//)
//
//@HiltViewModel
//class JournalTemplatesViewModel @Inject constructor() : ViewModel() {
//
//    private val _uiState = MutableStateFlow(
//        JournalTemplatesUiState(
//            templates = listOf(
//                JournalTemplate(
//                    id = "daily_reflection",
//                    name = "Daily Reflection",
//                    description = "A simple check-in to reflect on your day.",
//                    contentPrompt = "What was the highlight of your day? What's one thing you're grateful for?"
//                ),
//                JournalTemplate(
//                    id = "gratitude",
//                    name = "Gratitude",
//                    description = "Focus on the positive things in your life.",
//                    contentPrompt = "List 3 things you are grateful for today and why."
//                ),
//                JournalTemplate(
//                    id = "freestyle",
//                    name = "Freestyle",
//                    description = "Write whatever is on your mind.",
//                    contentPrompt = "Start writing..."
//                )
//            )
//        )
//    )
//    val uiState: StateFlow<JournalTemplatesUiState> = _uiState.asStateFlow()
//}