package com.githukudenis.todoey.ui.task_detail

import com.githukudenis.todoey.data.local.Priority
import com.githukudenis.todoey.data.local.TaskEntity
import com.githukudenis.todoey.util.UserMessage

sealed interface TaskDetailEvent {
    data class ChangeTaskPriority(val priority: Priority) : TaskDetailEvent
    data class SaveTask(val taskEntity: TaskEntity) : TaskDetailEvent
    data class ShowUserMessage(val userMessage: UserMessage) : TaskDetailEvent
    data class DismissUserMessage(val userMessage: UserMessage) : TaskDetailEvent
}