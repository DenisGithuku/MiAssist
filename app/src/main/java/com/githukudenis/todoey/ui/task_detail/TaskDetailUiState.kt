package com.githukudenis.todoey.ui.task_detail

import com.githukudenis.todoey.data.local.Priority
import com.githukudenis.todoey.data.local.TaskEntity
import com.githukudenis.todoey.util.UserMessage

data class TaskDetailUiState(
    val taskDetail: TaskEntity? = null,
    val priorities: List<Priority> = listOf(Priority.HIGH, Priority.MODERATE, Priority.LOW),
    val userMessages: List<UserMessage> = emptyList()
)
