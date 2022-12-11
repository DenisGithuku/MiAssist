package com.githukudenis.todoey.ui.todo_list

import com.githukudenis.todoey.util.OrderType
import com.githukudenis.todoey.util.SortType

sealed interface TodoEvent {
    data class ChangeSortType(val sortType: SortType) : TodoEvent
    data class ChangeOrderType(val orderType: OrderType) : TodoEvent
}