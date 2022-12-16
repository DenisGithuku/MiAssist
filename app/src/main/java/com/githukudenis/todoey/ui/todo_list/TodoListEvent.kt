package com.githukudenis.todoey.ui.todo_list

import com.githukudenis.todoey.data.local.Priority
import com.githukudenis.todoey.util.OrderType
import com.githukudenis.todoey.util.SortType

sealed interface TodoListEvent {
    data class ChangeSortType(val sortType: SortType) : TodoListEvent
    data class ChangeOrderType(val orderType: OrderType) : TodoListEvent
    data class ChangePriorityFilter(val priority: Priority) : TodoListEvent
}