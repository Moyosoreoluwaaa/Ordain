package com.ordain.ui.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ordain.domain.model.Todo
import com.ordain.domain.usecase.AddTodo
import com.ordain.domain.usecase.CompleteTodo
import com.ordain.domain.usecase.DeleteTodo
import com.ordain.domain.usecase.GetTodos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TodoUiState(
    val todos: List<Todo> = emptyList(),
    val newTodoInput: String = ""
)

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getTodos: GetTodos,
    private val addTodo: AddTodo,
    private val completeTodo: CompleteTodo,
    private val deleteTodo: DeleteTodo
) : ViewModel() {

    private val _uiState = MutableStateFlow(TodoUiState())
    val uiState: StateFlow<TodoUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getTodos().collect { todos ->
                _uiState.update { it.copy(todos = todos) }
            }
        }
    }

    fun onNewTodoInputChanged(input: String) {
        _uiState.update { it.copy(newTodoInput = input) }
    }

    fun onAddTodoClicked() {
        viewModelScope.launch {
            val title = _uiState.value.newTodoInput.trim()
            if (title.isNotBlank()) {
                addTodo(title)
                _uiState.update { it.copy(newTodoInput = "") }
            }
        }
    }

    fun onTodoToggled(todo: Todo) {
        viewModelScope.launch {
            completeTodo(todo, !todo.isCompleted)
        }
    }

    fun onTodoDeleted(todo: Todo) {
        viewModelScope.launch {
            deleteTodo(todo)
        }
    }
}