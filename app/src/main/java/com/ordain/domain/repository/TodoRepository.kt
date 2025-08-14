package com.ordain.domain.repository

import com.ordain.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getTodos(): Flow<List<Todo>>
    suspend fun addTodo(todo: Todo)
    suspend fun completeTodo(todo: Todo)
    suspend fun deleteTodo(todo: Todo)
}