package com.githukudenis.todoey.data.local

import com.githukudenis.todoey.domain.TasksRepository
import com.githukudenis.todoey.util.OrderType
import com.githukudenis.todoey.util.SortType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor(
    private val tasksDataSource: TasksDataSource
) : TasksRepository {
    override suspend fun addTask(taskEntity: TaskEntity) {
        return tasksDataSource.addTask(taskEntity)
    }

    override suspend fun getAllTasks(sortType: SortType, orderType: OrderType): Flow<List<TaskEntity>> {
        return tasksDataSource.getAllTasks(sortType = sortType, orderType = orderType)
    }

    override suspend fun deleteTask(taskEntity: TaskEntity) {
        tasksDataSource.deleteTask(taskEntity)
    }

    override suspend fun getTaskById(todoId: Long): Flow<TaskEntity?> {
        return tasksDataSource.getTaskById(todoId)
    }
}