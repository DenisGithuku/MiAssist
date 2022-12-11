package com.githukudenis.todoey.ui.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.todoey.data.local.TodoEntity
import com.githukudenis.todoey.domain.TodosRepository
import com.githukudenis.todoey.util.OrderType
import com.githukudenis.todoey.util.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodosListViewModel @Inject constructor(
    private val todosRepository: TodosRepository
) : ViewModel() {

    private val _state = MutableStateFlow<List<TodoEntity>>(emptyList())
    val state: StateFlow<List<TodoEntity>> get() = _state

    init {
        getAllTodos()
    }

    fun onEvent(todoEvent: TodoEvent) {
        when (todoEvent) {
            is TodoEvent.ChangeOrderType -> {
                getAllTodos(orderType = todoEvent.orderType)
            }
            is TodoEvent.ChangeSortType -> {
                getAllTodos(sortType = todoEvent.sortType)
            }
        }
    }

    fun getAllTodos(sortType: SortType = SortType.DUE_DATE, orderType: OrderType = OrderType.DESCENDING) {
        viewModelScope.launch {
            todosRepository.getAllTodos(sortType = sortType, orderType = orderType).collect { todos ->
                _state.value = todos
            }
        }
    }
}