package com.githukudenis.todoey.ui.add_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.todoey.data.local.TodoEntity
import com.githukudenis.todoey.domain.TodosRepository
import com.githukudenis.todoey.util.UserMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val todosRepository: TodosRepository
) : ViewModel() {

    private var _state = MutableStateFlow(AddTaskUiState())
    val state: StateFlow<AddTaskUiState> get() = _state

    fun saveTask(todoEntity: TodoEntity) {
        viewModelScope.launch {
            todosRepository.addTodo(todoEntity)
            _state.update { state ->
                state.copy(todoAdded = true)
            }
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
                saveTask(addTaskEvent.todoEntity)
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
        }
    }
}