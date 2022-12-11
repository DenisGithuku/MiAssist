package com.githukudenis.todoey.data.local

import android.util.Log
import com.githukudenis.todoey.domain.TodosRepository
import com.githukudenis.todoey.util.OrderType
import com.githukudenis.todoey.util.SortType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

private const val TAG = "TodosDataSourceError"

class TodosDataSource @Inject constructor(
    private val todosDao: TodosDao
) : TodosRepository {
    override suspend fun addTodo(todoEntity: TodoEntity) {
        return todosDao.insertTodo(todoEntity)
    }

    override suspend fun getAllTodos(
        sortType: SortType,
        orderType: OrderType
    ): Flow<List<TodoEntity>> = flow<List<TodoEntity>> {
        try {
            val todos = todosDao.getAllTodos()
            when (orderType) {
                OrderType.ASCENDING -> {
                    when (sortType) {
                        SortType.PRIORITY -> {
                            val sortedTodos = todos.sortedBy { todo -> todo.priority }
                            emit(sortedTodos)
                        }

                        SortType.DUE_DATE -> {
                            val sortedTodos = todos.sortedBy { todo -> todo.todoDueDate }
                            emit(sortedTodos)
                        }

                        SortType.DUE_TIME -> {
                            val sortedTodos = todos.sortedBy { todo -> todo.todoDueTime }
                            emit(sortedTodos)
                        }
                    }
                }

                OrderType.DESCENDING -> {
                    when (sortType) {
                        SortType.PRIORITY -> {
                            val sortedTodos = todos.sortedByDescending { todo -> todo.priority }
                            emit(sortedTodos)
                        }

                        SortType.DUE_DATE -> {
                            val sortedTodos = todos.sortedByDescending { todo -> todo.todoDueDate }
                            emit(sortedTodos)
                        }

                        SortType.DUE_TIME -> {
                            val sortedTodos = todos.sortedByDescending { todo -> todo.todoDueTime }
                            emit(sortedTodos)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "getAllTodos: ${e.localizedMessage}")
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteTodo(todoEntity: TodoEntity) {
        try {
            todosDao.deleteTodo(todoEntity.todoId ?: return)
        } catch (e: Exception) {
            Log.e(TAG, "deleteTodo: ${e.localizedMessage}")
        }
    }

    override suspend fun getTodoById(todoId: Long): Flow<TodoEntity?> = flow<TodoEntity?> {
        try {
            val todo = todosDao.getTodoById(todoId)
            emit(todo)
        } catch (e: Exception) {
            Log.e(TAG, "getTodoById: ${e.message}")
            emit(null)
        }
    }.flowOn(Dispatchers.IO)
}