package com.githukudenis.tasks.ui.task_detail

import com.denisgithuku.tasks.data.local.TaskEntity
import com.githukudenis.tasks.util.UserMessage

sealed interface TaskDetailEvent {
    object MarkComplete : TaskDetailEvent
    object DeleteTask : TaskDetailEvent
    data class UpdateTask(val taskEntity: TaskEntity) : TaskDetailEvent
    data class ShowUserMessage(val userMessage: UserMessage) : TaskDetailEvent
    data class DismissUserMessage(val userMessage: UserMessage) : TaskDetailEvent
}