package com.githukudenis.todoey.ui.task_list

import com.githukudenis.todoey.data.local.Priority
import com.githukudenis.todoey.data.local.TaskEntity
import com.githukudenis.todoey.util.OrderType
import com.githukudenis.todoey.util.SortType
import com.githukudenis.todoey.util.UserMessage

data class TaskListUiState(
    val tasks: List<TaskEntity> = emptyList(),
    val selectedPriority: Priority = Priority.HIGH,
    val selectedSortType: SortType = SortType.DUE_TIME,
    val selectedOrderType: OrderType = OrderType.DESCENDING,
    val userMessages: List<UserMessage> = emptyList()
)
