package com.githukudenis.todoey.ui.task_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.todoey.data.local.TaskEntity
import com.githukudenis.todoey.domain.TasksRepository
import com.githukudenis.todoey.util.UserMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val tasksRepository: TasksRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(TaskDetailUiState())
    val state: StateFlow<TaskDetailUiState> get() = _state

    init {
        savedStateHandle.get<String>("taskId")?.let { taskId ->
            getTaskById(taskId.toLong())
        }
    }

    fun onEvent(taskDetailEvent: TaskDetailEvent) {
        when (taskDetailEvent) {
            is TaskDetailEvent.ChangeTaskPriority -> {
                _state.update { prevState ->
                    prevState.copy(
                        taskDetail = _state.value.taskDetail?.copy(priority = taskDetailEvent.priority)
                    )
                }
            }
            is TaskDetailEvent.SaveTask -> {
                saveTask(taskDetailEvent.taskEntity)
            }
            is TaskDetailEvent.ShowUserMessage -> {
                val userMessages = mutableListOf<UserMessage>()
                userMessages.add(taskDetailEvent.userMessage)
                _state.update { prevState ->
                    prevState.copy(userMessages = userMessages)
                }
            }
            is TaskDetailEvent.DismissUserMessage -> {
                val userMessages = _state.value.userMessages.filterNot { userMessage -> userMessage == taskDetailEvent.userMessage }
                _state.update { prevState ->
                    prevState.copy(
                        userMessages = userMessages
                    )
                }
            }
        }
    }

    fun getTaskById(taskId: Long) {
        viewModelScope.launch {
            tasksRepository.getTaskById(taskId).collect { taskDetail ->
                _state.update { prevState ->
                    prevState.copy(
                        taskDetail = taskDetail
                    )
                }
            }
        }
    }

    fun saveTask(taskEntity: TaskEntity) {
        viewModelScope.launch {
            tasksRepository.updateTask(
                taskTitle = taskEntity.taskTitle,
                taskDescription = taskEntity.taskDescription ?: return@launch,
                taskDueDate = taskEntity.taskDueDate ?: return@launch,
                taskDueTime = taskEntity.taskDueTime ?: return@launch,
                completed = taskEntity.completed,
                priority = taskEntity.priority,
                taskId = _state.value.taskDetail?.taskId ?: return@launch
            )
        }
    }
}