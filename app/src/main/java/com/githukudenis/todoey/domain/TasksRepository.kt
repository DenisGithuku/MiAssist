package com.githukudenis.todoey.domain

import com.githukudenis.todoey.data.local.Priority
import com.githukudenis.todoey.data.local.TaskEntity
import com.githukudenis.todoey.util.OrderType
import com.githukudenis.todoey.util.SortType
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalTime

interface TasksRepository {
    suspend fun addTask(taskEntity: TaskEntity)

    suspend fun getAllTasks(sortType: SortType, orderType: OrderType): Flow<List<TaskEntity>>

    suspend fun deleteTask(taskEntity: TaskEntity)

    suspend fun getTaskById(todoId: Long): Flow<TaskEntity?>

    suspend fun updateTask(
        taskTitle: String,
        taskDescription: String,
        taskDueDate: LocalDate,
        taskDueTime: LocalTime,
        completed: Boolean,
        priority: Priority,
        taskId: Long
    )
}