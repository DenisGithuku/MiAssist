package com.githukudenis.tasks.ui.task_list

import com.denisgithuku.tasks.data.local.Priority
import com.denisgithuku.tasks.data.local.TaskEntity
import com.githukudenis.tasks.util.OrderType
import com.githukudenis.tasks.util.SortType
import com.githukudenis.tasks.util.UserMessage

data class TaskListUiState(
    val tasks: List<TaskEntity> = emptyList(),
    val selectedPriority: Priority = Priority.HIGH,
    val selectedSortType: SortType = SortType.DUE_TIME,
    val selectedOrderType: OrderType = OrderType.DESCENDING,
    val userMessages: List<UserMessage> = emptyList()
)
