package com.githukudenis.todoey.ui.task_list

import com.githukudenis.todoey.data.local.Priority
import com.githukudenis.todoey.util.OrderType
import com.githukudenis.todoey.util.SortType

sealed interface TaskListEvent {
    data class ChangeSortType(val sortType: SortType) : TaskListEvent
    data class ChangeOrderType(val orderType: OrderType) : TaskListEvent
    data class ChangePriorityFilter(val priority: Priority) : TaskListEvent
    data class ToggleCompleteTask(val taskId: Long) : TaskListEvent
}