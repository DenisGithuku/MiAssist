package com.githukudenis.tasks.domain

import com.denisgithuku.tasks.data.local.TaskEntity
import com.githukudenis.tasks.util.OrderType
import com.githukudenis.tasks.util.SortType
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    suspend fun addTask(taskEntity: TaskEntity)

    suspend fun getAllTasks(sortType: SortType, orderType: OrderType): Flow<List<TaskEntity>>

    suspend fun deleteTask(taskEntity: TaskEntity)

    suspend fun getTaskById(todoId: Long): Flow<TaskEntity?>

    suspend fun updateTask(
        taskEntity: TaskEntity
    )

    suspend fun setTaskReminder(
        alarmTime: Long,
        taskTitle: String
    )
}