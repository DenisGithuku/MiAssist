package com.githukudenis.tasks.ui.task_list

import com.denisgithuku.tasks.data.local.Priority
import com.denisgithuku.tasks.data.local.TaskEntity
import com.githukudenis.core_data.util.UserMessage
import com.githukudenis.tasks.util.OrderType
import com.githukudenis.tasks.util.SortType

data class TaskListUiState(
    val tasks: List<TaskEntity> = emptyList(),
    val selectedPriority: Priority = Priority.HIGH,
    val selectedSortType: SortType = SortType.DUE_TIME,
    val selectedOrderType: OrderType = OrderType.DESCENDING,
    val userMessages: List<UserMessage> = emptyList()
)
