package com.githukudenis.miassist.data

import com.githukudenis.tasks.domain.TasksRepository
import com.githukudenis.miassist.data.local.TaskEntity
import com.githukudenis.miassist.util.OrderType
import com.githukudenis.miassist.util.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

class FakeTasksRepository : TasksRepository {

    private var tasks = mutableListOf<TaskEntity>()
    private var observableTasks = MutableStateFlow<List<TaskEntity>>(emptyList())

    private fun refreshObservableTasks() {
        observableTasks.value = tasks
    }

    override suspend fun addTask(taskEntity: TaskEntity) {
        tasks.add(taskEntity)
        refreshObservableTasks()
    }

    override suspend fun getAllTasks(sortType: SortType, orderType: OrderType): Flow<List<TaskEntity>> {
        return observableTasks
    }

    override suspend fun deleteTask(taskEntity: TaskEntity) {
        tasks.remove(taskEntity)
        refreshObservableTasks()
    }

    override suspend fun getTaskById(todoId: Long): Flow<TaskEntity?> {
        val task = observableTasks.value.find { todo -> todo.taskId == todoId }
        return flowOf(task)
    }

    override suspend fun updateTask(
        taskEntity: TaskEntity
    ) {
        tasks.remove(tasks.find { task -> task.taskId == taskEntity.taskId })
        tasks.add(taskEntity)
        refreshObservableTasks()
    }
}