package com.ordain.data.repository

import com.ordain.data.local.source.TodoLocalDataSource
import com.ordain.data.model.TodoEntity
import com.ordain.domain.model.Todo
import com.ordain.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(private val localDataSource: TodoLocalDataSource) : TodoRepository {

    override fun getTodos(): Flow<List<Todo>> {
        return localDataSource.getAllTodos().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun addTodo(todo: Todo) {
        localDataSource.addTodo(todo.toDataModel())
    }

    override suspend fun completeTodo(todo: Todo) {
        val completedTodo = todo.copy(isCompleted = !todo.isCompleted)
        localDataSource.updateTodo(completedTodo.toDataModel())
    }

    override suspend fun deleteTodo(todo: Todo) {
        localDataSource.deleteTodo(todo.toDataModel())
    }

    private fun TodoEntity.toDomainModel(): Todo {
        return Todo(
            id = id,
            title = title,
            isCompleted = isCompleted,
            timestamp = timestamp
        )
    }

    private fun Todo.toDataModel(): TodoEntity {
        return TodoEntity(
            id = id,
            title = title,
            isCompleted = isCompleted,
            timestamp = timestamp
        )
    }
}