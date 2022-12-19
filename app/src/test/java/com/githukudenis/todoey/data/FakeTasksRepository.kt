package com.githukudenis.todoey.data

import com.githukudenis.todoey.data.local.TaskEntity
import com.githukudenis.todoey.domain.TasksRepository
import com.githukudenis.todoey.util.OrderType
import com.githukudenis.todoey.util.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

class FakeTasksRepository : TasksRepository {

    private var todos = mutableListOf<TaskEntity>()
    private var observableTodos = MutableStateFlow<List<TaskEntity>>(emptyList())

    private fun refreshObservableTodos() {
        observableTodos.value = todos
    }

    override suspend fun addTask(taskEntity: TaskEntity) {
        todos.add(taskEntity)
        refreshObservableTodos()
    }

    override suspend fun getAllTasks(sortType: SortType, orderType: OrderType): Flow<List<TaskEntity>> {
        return observableTodos
    }

    override suspend fun deleteTask(taskEntity: TaskEntity) {
        todos.remove(taskEntity)
        refreshObservableTodos()
    }

    override suspend fun getTaskById(todoId: Long): Flow<TaskEntity?> {
        val todo = observableTodos.value.find { todo -> todo.taskId == todoId }
        return flowOf(todo)
    }
}