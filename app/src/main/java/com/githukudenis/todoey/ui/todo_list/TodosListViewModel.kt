package com.githukudenis.todoey.ui.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.todoey.data.local.Priority
import com.githukudenis.todoey.domain.TodosRepository
import com.githukudenis.todoey.util.OrderType
import com.githukudenis.todoey.util.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodosListViewModel @Inject constructor(
    private val todosRepository: TodosRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TodoListUiState())
    val state: StateFlow<TodoListUiState> get() = _state

    init {
        getAllTodos()
    }

    fun onEvent(todoListEvent: TodoListEvent) {
        when (todoListEvent) {
            is TodoListEvent.ChangeOrderType -> {
                getAllTodos(orderType = todoListEvent.orderType)
            }
            is TodoListEvent.ChangeSortType -> {
                getAllTodos(sortType = todoListEvent.sortType)
            }

            is TodoListEvent.ChangePriorityFilter -> {
                changePriority(todoListEvent.priority)
            }
        }
    }

    fun changePriority(priority: Priority) {
        viewModelScope.launch {
            todosRepository.getAllTodos(sortType = SortType.DUE_DATE, orderType = OrderType.DESCENDING).collect { todos ->
                val filteredTodos = todos.filter { todoEntity -> todoEntity.priority == priority }
                _state.update {
                    it.copy(
                        todos = filteredTodos,
                        selectedPriority = priority
                    )
                }
            }
        }
    }

    fun getAllTodos(sortType: SortType = SortType.DUE_DATE, orderType: OrderType = OrderType.DESCENDING) {
        viewModelScope.launch {
            todosRepository.getAllTodos(sortType = sortType, orderType = orderType).collect { todos ->
                _state.update {
                    it.copy(
                        todos = todos
                    )
                }
            }
        }
    }
}