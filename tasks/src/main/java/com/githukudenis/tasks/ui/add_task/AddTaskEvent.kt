package com.githukudenis.tasks.ui.add_task

import com.denisgithuku.tasks.data.local.Priority
import com.denisgithuku.tasks.data.local.TaskEntity
import com.githukudenis.tasks.util.UserMessage

sealed interface AddTaskEvent {
    data class ChangeTaskPriority(val priority: Priority) : AddTaskEvent
    data class SaveTask(val taskEntity: TaskEntity) : AddTaskEvent
    data class ShowUserMessage(val userMessage: UserMessage) : AddTaskEvent
    data class DismissUserMessage(val userMessage: UserMessage) : AddTaskEvent
}