package com.githukudenis.todoey.ui.todo_list

import com.githukudenis.todoey.data.local.Priority
import com.githukudenis.todoey.data.local.TodoEntity
import com.githukudenis.todoey.util.UserMessage

data class TodoListUiState(
    val todos: List<TodoEntity> = emptyList(),
    val selectedPriority: Priority = Priority.HIGH,
    val userMessages: List<UserMessage> = emptyList()
)
