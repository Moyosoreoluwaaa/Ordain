package com.ordain.data.local.source

import com.ordain.data.local.TodoDao
import com.ordain.data.model.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoLocalDataSource {
    fun getAllTodos(): Flow<List<TodoEntity>>
    suspend fun addTodo(todo: TodoEntity)
    suspend fun updateTodo(todo: TodoEntity)
    suspend fun deleteTodo(todo: TodoEntity)
}

class TodoLocalDataSourceImpl(private val todoDao: TodoDao) : TodoLocalDataSource {
    override fun getAllTodos(): Flow<List<TodoEntity>> = todoDao.getAllTodos()
    override suspend fun addTodo(todo: TodoEntity) = todoDao.insertTodo(todo)
    override suspend fun updateTodo(todo: TodoEntity) = todoDao.updateTodo(todo)
    override suspend fun deleteTodo(todo: TodoEntity) = todoDao.deleteTodo(todo)
}