package com.githukudenis.todoey.domain

import com.githukudenis.todoey.data.local.TodoEntity
import com.githukudenis.todoey.util.OrderType
import com.githukudenis.todoey.util.SortType
import kotlinx.coroutines.flow.Flow

interface TodosRepository {
    suspend fun addTodo(todoEntity: TodoEntity)

    suspend fun getAllTodos(sortType: SortType, orderType: OrderType): Flow<List<TodoEntity>>

    suspend fun deleteTodo(todoEntity: TodoEntity)

    suspend fun getTodoById(todoId: Long): Flow<TodoEntity?>
}