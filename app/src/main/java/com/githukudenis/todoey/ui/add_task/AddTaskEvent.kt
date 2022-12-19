package com.githukudenis.todoey.ui.add_task

import com.githukudenis.todoey.data.local.Priority
import com.githukudenis.todoey.data.local.TaskEntity
import com.githukudenis.todoey.util.UserMessage

sealed interface AddTaskEvent {
    data class ChangeTaskPriority(val priority: Priority) : AddTaskEvent
    data class SaveTask(val taskEntity: TaskEntity) : AddTaskEvent
    data class ShowUserMessage(val userMessage: UserMessage) : AddTaskEvent
    data class DismissUserMessage(val userMessage: UserMessage) : AddTaskEvent
}