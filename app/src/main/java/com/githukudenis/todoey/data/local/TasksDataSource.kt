package com.githukudenis.todoey.data.local

import android.util.Log
import com.githukudenis.todoey.domain.TasksRepository
import com.githukudenis.todoey.util.OrderType
import com.githukudenis.todoey.util.SortType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

private const val TAG = "TasksDataSourceError"

class TasksDataSource @Inject constructor(
    private val tasksDao: TasksDao
) : TasksRepository {
    override suspend fun addTask(taskEntity: TaskEntity) {
        return tasksDao.insertTask(taskEntity)
    }

    override suspend fun getAllTasks(
        sortType: SortType,
        orderType: OrderType
    ): Flow<List<TaskEntity>> = flow {
        try {
            val todos = tasksDao.getAllTasks()
            when (orderType) {
                OrderType.ASCENDING -> {
                    when (sortType) {
                        SortType.PRIORITY -> {
                            val sortedTodos = todos.sortedBy { todo -> todo.priority }
                            emit(sortedTodos)
                        }

                        SortType.DUE_DATE -> {
                            val sortedTodos = todos.sortedBy { todo -> todo.taskDueDate }
                            emit(sortedTodos)
                        }

                        SortType.DUE_TIME -> {
                            val sortedTodos = todos.sortedBy { todo -> todo.taskDueTime }
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
                            val sortedTodos = todos.sortedByDescending { todo -> todo.taskDueDate }
                            emit(sortedTodos)
                        }

                        SortType.DUE_TIME -> {
                            val sortedTodos = todos.sortedByDescending { todo -> todo.taskDueTime }
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

    override suspend fun deleteTask(taskEntity: TaskEntity) {
        try {
            tasksDao.deleteTask(taskEntity.taskId ?: return)
        } catch (e: Exception) {
            Log.e(TAG, "deleteTodo: ${e.localizedMessage}")
        }
    }

    override suspend fun getTaskById(todoId: Long): Flow<TaskEntity?> = flow {
        try {
            val todo = tasksDao.getTaskById(todoId)
            emit(todo)
        } catch (e: Exception) {
            Log.e(TAG, "getTodoById: ${e.message}")
            emit(null)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun updateTask(
        taskEntity: TaskEntity
    ) {
        try {
            tasksDao.updateTask(
                taskTitle = taskEntity.taskTitle,
                taskDescription = taskEntity.taskDescription ?: return,
                taskDueDate = taskEntity.taskDueDate ?: return,
                taskDueTime = taskEntity.taskDueTime ?: return,
                completed = taskEntity.completed,
                priority = taskEntity.priority,
                taskId = taskEntity.taskId ?: return

            )
        } catch (e: Exception) {
            Log.e(TAG, "toggleCompleteTask: ${e.localizedMessage}")
        }
    }
}