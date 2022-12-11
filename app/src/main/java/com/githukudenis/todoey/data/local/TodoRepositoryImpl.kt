package com.githukudenis.todoey.data.local

import com.githukudenis.todoey.domain.TodosRepository
import com.githukudenis.todoey.util.OrderType
import com.githukudenis.todoey.util.SortType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todosDataSource: TodosDataSource
) : TodosRepository {
    override suspend fun addTodo(todoEntity: TodoEntity) {
        return todosDataSource.addTodo(todoEntity)
    }

    override suspend fun getAllTodos(sortType: SortType, orderType: OrderType): Flow<List<TodoEntity>> {
        return todosDataSource.getAllTodos(sortType = sortType, orderType = orderType)
    }

    override suspend fun deleteTodo(todoEntity: TodoEntity) {
        todosDataSource.deleteTodo(todoEntity)
    }

    override suspend fun getTodoById(todoId: Long): Flow<TodoEntity?> {
        return todosDataSource.getTodoById(todoId)
    }
}