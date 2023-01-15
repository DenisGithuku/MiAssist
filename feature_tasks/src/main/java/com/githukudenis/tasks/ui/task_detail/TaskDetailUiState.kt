package com.githukudenis.tasks.ui.task_detail

import com.denisgithuku.tasks.data.local.Priority
import com.denisgithuku.tasks.data.local.TaskEntity
import com.githukudenis.core_data.util.UserMessage

data class TaskDetailUiState(
    val taskDetail: TaskEntity? = null,
    val priorities: List<Priority> = listOf(Priority.HIGH, Priority.MODERATE, Priority.LOW),
    val userMessages: List<UserMessage> = emptyList()
)
