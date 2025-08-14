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
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TodoListUiState(
    val todos: List<Todo> = emptyList(),
    val newTodoTitle: String = ""
)

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val getTodos: GetTodos,
    private val addTodo: AddTodo,
    private val completeTodo: CompleteTodo,
    private val deleteTodo: DeleteTodo
) : ViewModel() {

    private val _uiState = MutableStateFlow(TodoListUiState())
    val uiState: StateFlow<TodoListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getTodos().collect { todos ->
                _uiState.value = _uiState.value.copy(todos = todos)
            }
        }
    }

    fun onNewTodoTitleChange(newTitle: String) {
        _uiState.value = _uiState.value.copy(newTodoTitle = newTitle)
    }

    fun onAddTodo() {
        viewModelScope.launch {
            addTodo(uiState.value.newTodoTitle)
            _uiState.value = _uiState.value.copy(newTodoTitle = "")
        }
    }

    fun onToggleTodoCompletion(todo: Todo) {
        viewModelScope.launch {
            completeTodo(todo, !todo.isCompleted)
        }
    }

    fun onDeleteTodo(todo: Todo) {
        viewModelScope.launch {
            deleteTodo(todo)
        }
    }
}