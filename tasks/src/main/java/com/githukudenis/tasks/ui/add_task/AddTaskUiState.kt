package com.githukudenis.tasks.ui.add_task

import com.denisgithuku.tasks.data.local.Priority
import com.githukudenis.tasks.util.UserMessage

data class AddTaskUiState(
    val todoState: TodoState = TodoState(),
    val todoAdded: Boolean = false,
    val priority: Priority = Priority.LOW,
    val priorities: List<Priority> = listOf(Priority.HIGH, Priority.MODERATE, Priority.LOW),
    val userMessages: List<UserMessage> = emptyList()
)

data class TodoState(
    val title: String? = null,
    val description: String? = null,
    val dueDate: Long? = null,
    val dueTime: Long? = null,
    val completed: Boolean = false,
    val priority: Priority = Priority.LOW
)