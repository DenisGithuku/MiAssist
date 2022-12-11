package com.githukudenis.todoey.data.local

import android.util.Log
import com.githukudenis.todoey.domain.TodosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

private val TAG = "repo_exception"

class TodoRepositoryImpl @Inject constructor(
    private val todosDao: TodosDao
) : TodosRepository {
    override suspend fun addTodo(todoEntity: TodoEntity) {
        try {
            todosDao.insertTodo(todoEntity)
        } catch (e: Exception) {
            Log.e(TAG, "addTodo: Failed with exception ${e.message}")
        }
    }

    override suspend fun getAllTodos(): Flow<List<TodoEntity>> {
        return try {
            flowOf(todosDao.getAllTodos())
        } catch (e: Exception) {
            Log.e(TAG, "getAllTodos: Failed with exception ${e.message} ")
            flowOf(emptyList())
        }
    }

    override suspend fun deleteTodo(todoEntity: TodoEntity) {
        try {
            todoEntity.todoId?.let { todoId ->
                todosDao.deleteTodo(todoId)
            }
        } catch (e: Exception) {
            Log.e(TAG, "deleteTodo: Failed with exception ${e.message}")
        }
    }

    override suspend fun getTodoById(todoId: Long): Flow<TodoEntity> {
        return try {
            val todo = todosDao.getTodoById(todoId)
            flowOf(todo)
        } catch (e: Exception) {
            flowOf()
        }
    }
}