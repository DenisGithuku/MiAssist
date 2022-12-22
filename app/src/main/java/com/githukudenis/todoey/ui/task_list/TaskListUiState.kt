package com.githukudenis.todoey.ui.task_list

import com.githukudenis.todoey.data.local.Priority
import com.githukudenis.todoey.data.local.TaskEntity
import com.githukudenis.todoey.util.UserMessage

data class TaskListUiState(
    val tasks: List<TaskEntity> = emptyList(),
    val selectedPriority: Priority = Priority.HIGH,
    val userMessages: List<UserMessage> = emptyList()
)
