package com.githukudenis.todoey.ui.add_task

import com.githukudenis.todoey.data.local.Priority
import com.githukudenis.todoey.data.local.TodoEntity
import com.githukudenis.todoey.util.UserMessage

sealed interface AddTaskEvent {
    data class ChangeTaskPriority(val priority: Priority) : AddTaskEvent
    data class SaveTask(val todoEntity: TodoEntity) : AddTaskEvent
    data class ShowUserMessage(val userMessage: UserMessage) : AddTaskEvent
    data class DismissUserMessage(val userMessage: UserMessage) : AddTaskEvent
}