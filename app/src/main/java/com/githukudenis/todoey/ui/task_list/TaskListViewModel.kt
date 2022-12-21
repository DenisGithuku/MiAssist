package com.githukudenis.todoey.ui.task_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.todoey.data.local.Priority
import com.githukudenis.todoey.data.local.TaskEntity
import com.githukudenis.todoey.domain.TasksRepository
import com.githukudenis.todoey.util.OrderType
import com.githukudenis.todoey.util.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val tasksRepository: TasksRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TaskListUiState())
    val state: StateFlow<TaskListUiState> get() = _state

    init {
        getAllTodos()
    }

    fun onEvent(taskListEvent: TaskListEvent) {
        when (taskListEvent) {
            is TaskListEvent.ChangeOrderType -> {
                getAllTodos(orderType = taskListEvent.orderType)
            }
            is TaskListEvent.ChangeSortType -> {
                getAllTodos(sortType = taskListEvent.sortType)
            }

            is TaskListEvent.ChangePriorityFilter -> {
                changePriority(taskListEvent.priority)
            }

            is TaskListEvent.ToggleCompleteTask -> {
                val taskEntity = _state.value.tasks.find { task -> task.taskId == taskListEvent.taskId }
                taskEntity?.copy(
                    completed = !taskEntity.completed
                ) ?: return
                updateTask(
                    taskEntity = taskEntity
                )
                getAllTodos()
            }
        }
    }

    fun changePriority(priority: Priority) {
        viewModelScope.launch {
            tasksRepository.getAllTasks(sortType = SortType.DUE_DATE, orderType = OrderType.DESCENDING).collect { tasks ->
                val filteredTasks = tasks.filter { todoEntity -> todoEntity.priority == priority }
                _state.update {
                    it.copy(
                        tasks = filteredTasks,
                        selectedPriority = priority
                    )
                }
            }
        }
    }

    fun getAllTodos(sortType: SortType = SortType.DUE_DATE, orderType: OrderType = OrderType.DESCENDING) {
        viewModelScope.launch {
            tasksRepository.getAllTasks(sortType = sortType, orderType = orderType).collect { tasks ->
                _state.update {
                    val filteredTasks = tasks.filter { task -> task.priority == _state.value.selectedPriority }
                    it.copy(
                        tasks = filteredTasks
                    )
                }
            }
        }
    }

    fun updateTask(taskEntity: TaskEntity) {
        viewModelScope.launch {
            val task = TaskEntity(
                taskTitle = taskEntity.taskTitle,
                taskDescription = taskEntity.taskDescription ?: return@launch,
                taskDueDate = taskEntity.taskDueDate ?: return@launch,
                taskDueTime = taskEntity.taskDueTime ?: return@launch,
                completed = taskEntity.completed,
                priority = taskEntity.priority,
                taskId = taskEntity.taskId ?: return@launch
            )
            tasksRepository.updateTask(
                taskEntity
            )
        }
    }
}