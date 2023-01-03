package com.githukudenis.tasks.ui.task_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisgithuku.tasks.data.local.TaskEntity
import com.githukudenis.tasks.domain.TasksRepository
import com.githukudenis.tasks.util.UserMessage
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
            is TaskDetailEvent.UpdateTask -> {
                updateTask(taskDetailEvent.taskEntity)
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
            is TaskDetailEvent.MarkComplete -> {
                val task = _state.value.taskDetail?.let { taskEntity ->
                    taskEntity.copy(completed = !taskEntity.completed)
                }
                _state.update { prevState ->
                    prevState.copy(taskDetail = task)
                }
                updateTask(taskEntity = task ?: return)
            }
            is TaskDetailEvent.DeleteTask -> {
                deleteTask(taskEntity = _state.value.taskDetail ?: return)
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

    fun updateTask(taskEntity: TaskEntity) {
        viewModelScope.launch {
            tasksRepository.updateTask(taskEntity)
        }
    }

    fun deleteTask(taskEntity: TaskEntity) {
        viewModelScope.launch {
            tasksRepository.deleteTask(taskEntity)
        }
    }
}