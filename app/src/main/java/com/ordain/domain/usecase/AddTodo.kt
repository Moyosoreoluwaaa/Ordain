package com.ordain.domain.usecase

import com.ordain.domain.model.Todo
import com.ordain.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class AddTodo(private val repository: TodoRepository) {
    suspend operator fun invoke(title: String) {
        if (title.isBlank()) {
            // Can be implemented as a custom exception later
            throw IllegalArgumentException("Title cannot be blank")
        }
        val newTodo = Todo(
            id = UUID.randomUUID().toString(),
            title = title,
            timestamp = System.currentTimeMillis()
        )
        repository.addTodo(newTodo)
    }
}

class GetTodos(private val repository: TodoRepository) {
    operator fun invoke(): Flow<List<Todo>> {
        return repository.getTodos()
    }
}

class CompleteTodo(private val repository: TodoRepository) {
    suspend operator fun invoke(todo: Todo, bool: Boolean) {
        val completedTodo = todo.copy(isCompleted = !todo.isCompleted)
        repository.completeTodo(completedTodo)
    }
}

class DeleteTodo(private val repository: TodoRepository) {
    suspend operator fun invoke(todo: Todo) {
        repository.deleteTodo(todo)
    }
}