package com.githukudenis.tasks.ui.task_list

import com.denisgithuku.tasks.data.local.Priority
import com.githukudenis.tasks.util.OrderType
import com.githukudenis.tasks.util.SortType

sealed interface TaskListEvent {
    data class ChangeSortType(val sortType: SortType) : TaskListEvent
    data class ChangeOrderType(val orderType: OrderType) : TaskListEvent
    data class ChangePriorityFilter(val priority: Priority) : TaskListEvent
    data class ToggleCompleteTask(val taskId: Long) : TaskListEvent
}