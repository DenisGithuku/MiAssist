package com.githukudenis.todoey.ui.task_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.todoey.data.local.Priority
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
                val completed = _state.value.todos.find { task -> task.taskId == taskListEvent.taskId }?.completed ?: return

                toggleCompleteTask(
                    completed = !completed,
                    taskId = taskListEvent.taskId
                )
            }
        }
    }

    fun changePriority(priority: Priority) {
        viewModelScope.launch {
            tasksRepository.getAllTasks(sortType = SortType.DUE_DATE, orderType = OrderType.DESCENDING).collect { todos ->
                val filteredTodos = todos.filter { todoEntity -> todoEntity.priority == priority }
                _state.update {
                    it.copy(
                        todos = filteredTodos,
                        selectedPriority = priority
                    )
                }
            }
        }
    }

    fun getAllTodos(sortType: SortType = SortType.DUE_DATE, orderType: OrderType = OrderType.DESCENDING) {
        viewModelScope.launch {
            tasksRepository.getAllTasks(sortType = sortType, orderType = orderType).collect { todos ->
                _state.update {
                    it.copy(
                        todos = todos
                    )
                }
            }
        }
    }

    fun toggleCompleteTask(completed: Boolean, taskId: Long) {
        viewModelScope.launch {
            tasksRepository.toggleCompleteTask(completed = completed, taskId = taskId)
        }
    }
}