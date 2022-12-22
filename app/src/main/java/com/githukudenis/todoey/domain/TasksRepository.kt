package com.githukudenis.todoey.domain

import com.githukudenis.todoey.data.local.TaskEntity
import com.githukudenis.todoey.util.OrderType
import com.githukudenis.todoey.util.SortType
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    suspend fun addTask(taskEntity: TaskEntity)

    suspend fun getAllTasks(sortType: SortType, orderType: OrderType): Flow<List<TaskEntity>>

    suspend fun deleteTask(taskEntity: TaskEntity)

    suspend fun getTaskById(todoId: Long): Flow<TaskEntity?>

    suspend fun updateTask(
        taskEntity: TaskEntity
    )
}