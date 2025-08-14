package com.ordain.ui.journaling.entry

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ordain.domain.model.JournalEntry
import com.ordain.domain.usecase.DeleteJournalEntry
import com.ordain.domain.usecase.GetJournalEntry
import com.ordain.domain.usecase.SaveJournalEntry
import com.ordain.ui.journaling.templates.JournalTemplate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class JournalingUiState(
    val entryId: String? = null,
    val template: JournalTemplate? = null,
    val title: String = "",
    val content: String = ""
)

@HiltViewModel
class JournalingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val saveJournalEntry: SaveJournalEntry,
    private val getJournalEntry: GetJournalEntry,
    private val deleteJournalEntry: DeleteJournalEntry
) : ViewModel() {

    private val _uiState = MutableStateFlow(JournalingUiState())
    val uiState: StateFlow<JournalingUiState> = _uiState.asStateFlow()

    private val templates = listOf(
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
        val entryId: String? = savedStateHandle["entryId"]
        val templateId: String? = savedStateHandle["templateId"]

        if (entryId != null) {
            viewModelScope.launch {
                getJournalEntry(entryId).collect { entry ->
                    entry?.let {
                        _uiState.update {
                            it.copy(
                                entryId = entry.id,
                                template = templates.find { t -> t.id == entry.templateId },
                                title = entry.title,
                                content = entry.content
                            )
                        }
                    }
                }
            }
        } else if (templateId != null) {
            val selectedTemplate = templates.find { it.id == templateId }
            _uiState.update { it.copy(template = selectedTemplate) }
        }
    }

    fun onTitleChanged(newTitle: String) {
        _uiState.update { it.copy(title = newTitle) }
    }

    fun onContentChanged(newContent: String) {
        _uiState.update { it.copy(content = newContent) }
    }

    fun onSaveClicked() {
        val currentState = _uiState.value
        val templateId = currentState.template?.id ?: "freestyle"
        val title = currentState.title.trim()
        val content = currentState.content.trim()

        viewModelScope.launch {
            if (title.isNotBlank() && content.isNotBlank()) {
                saveJournalEntry(
                    id = currentState.entryId,
                    title = title,
                    content = content,
                    templateId = templateId
                )
            }
        }
    }

    fun onDeleteClicked(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val entryId = uiState.value.entryId ?: return@launch
            val entry = JournalEntry(
                id = entryId,
                title = uiState.value.title,
                content = uiState.value.content,
                templateId = uiState.value.template?.id ?: "freestyle",
                timestamp = 0L // Timestamp is not needed for deletion
            )
            deleteJournalEntry(entry)
            onSuccess()
        }
    }
}