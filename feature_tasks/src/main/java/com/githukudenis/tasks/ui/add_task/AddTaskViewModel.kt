package com.githukudenis.tasks.ui.add_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisgithuku.tasks.data.local.TaskEntity
import com.githukudenis.core_data.util.UserMessage
import com.githukudenis.tasks.domain.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val tasksRepository: TasksRepository
) : ViewModel() {

    private var _state = MutableStateFlow(AddTaskUiState())
    val state: StateFlow<AddTaskUiState> get() = _state

    private fun saveTask(taskEntity: TaskEntity) {
        viewModelScope.launch {
            tasksRepository.addTask(taskEntity)
            _state.update { state ->
                state.copy(todoAdded = true)
            }
        }
    }

    private fun setReminder(time: Long, taskTitle: String) {
        viewModelScope.launch {
            tasksRepository.setTaskReminder(
                alarmTime = time,
                taskTitle = taskTitle
            )
        }
    }

    fun onEvent(addTaskEvent: AddTaskEvent) {
        when (addTaskEvent) {
            is AddTaskEvent.ChangeTaskPriority -> {
                _state.update { state ->
                    state.copy(priority = addTaskEvent.priority)
                }
            }

            is AddTaskEvent.SaveTask -> {
                saveTask(addTaskEvent.taskEntity)
            }

            is AddTaskEvent.ShowUserMessage -> {
                val userMessages = mutableListOf<UserMessage>()
                userMessages.add(addTaskEvent.userMessage)
                _state.update { state ->
                    state.copy(userMessages = userMessages)
                }
            }

            is AddTaskEvent.DismissUserMessage -> {
                val userMessages = _state.value.userMessages.filterNot { message -> message == addTaskEvent.userMessage }
                _state.update {
                    it.copy(userMessages = userMessages)
                }
            }
            is AddTaskEvent.SetReminder -> {
                setReminder(
                    time = _state.value.reminderState.time ?: return,
                    taskTitle = _state.value.reminderState.title ?: return
                )
            }
            is AddTaskEvent.ChangeAlarmTime -> {
                _state.update { state ->
                    val reminderState = state.reminderState.copy(time = addTaskEvent.time)
                    state.copy(
                        reminderState = reminderState
                    )
                }
            }
            is AddTaskEvent.ChangeAlarmTitle -> {
                _state.update { state ->
                    val reminderState = state.reminderState.copy(title = addTaskEvent.title)
                    state.copy(reminderState = reminderState)
                }
            }
        }
    }
}