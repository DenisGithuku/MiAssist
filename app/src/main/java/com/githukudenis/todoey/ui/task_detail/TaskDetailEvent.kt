package com.githukudenis.todoey.ui.task_detail

import com.githukudenis.todoey.data.local.TaskEntity
import com.githukudenis.todoey.util.UserMessage

sealed interface TaskDetailEvent {
    data class UpdateTask(val taskEntity: TaskEntity) : TaskDetailEvent
    data class ShowUserMessage(val userMessage: UserMessage) : TaskDetailEvent
    data class DismissUserMessage(val userMessage: UserMessage) : TaskDetailEvent
}