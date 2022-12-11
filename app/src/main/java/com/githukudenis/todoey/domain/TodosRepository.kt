package com.githukudenis.todoey.domain

import com.githukudenis.todoey.data.local.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodosRepository {
    suspend fun addTodo(todoEntity: TodoEntity)

    suspend fun getAllTodos(): Flow<List<TodoEntity>>

    suspend fun deleteTodo(todoEntity: TodoEntity)

    suspend fun getTodoById(todoId: Long): Flow<TodoEntity?>
}