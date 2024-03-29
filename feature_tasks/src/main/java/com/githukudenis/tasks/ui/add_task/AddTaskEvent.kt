package com.githukudenis.tasks.ui.add_task

import com.denisgithuku.tasks.data.local.Priority
import com.denisgithuku.tasks.data.local.TaskEntity
import com.githukudenis.core_data.util.UserMessage

sealed interface AddTaskEvent {
    data class ChangeTaskPriority(val priority: Priority) : AddTaskEvent
    data class SaveTask(val taskEntity: TaskEntity) : AddTaskEvent
    data class ShowUserMessage(val userMessage: UserMessage) : AddTaskEvent
    data class DismissUserMessage(val userMessage: UserMessage) : AddTaskEvent
    object SetReminder : AddTaskEvent
    data class ChangeAlarmTime(val time: Long) : AddTaskEvent
    data class ChangeAlarmTitle(val title: String) : AddTaskEvent
}