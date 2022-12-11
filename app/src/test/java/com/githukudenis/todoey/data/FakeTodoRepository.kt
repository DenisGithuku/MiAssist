package com.githukudenis.todoey.data

import com.githukudenis.todoey.data.local.TodoEntity
import com.githukudenis.todoey.domain.TodosRepository
import com.githukudenis.todoey.util.OrderType
import com.githukudenis.todoey.util.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

class FakeTodoRepository : TodosRepository {

    private var todos = mutableListOf<TodoEntity>()
    private var observableTodos = MutableStateFlow<List<TodoEntity>>(emptyList())

    private fun refreshObservableTodos() {
        observableTodos.value = todos
    }

    override suspend fun addTodo(todoEntity: TodoEntity) {
        todos.add(todoEntity)
        refreshObservableTodos()
    }

    override suspend fun getAllTodos(sortType: SortType, orderType: OrderType): Flow<List<TodoEntity>> {
        return observableTodos
    }

    override suspend fun deleteTodo(todoEntity: TodoEntity) {
        todos.remove(todoEntity)
        refreshObservableTodos()
    }

    override suspend fun getTodoById(todoId: Long): Flow<TodoEntity?> {
        val todo = observableTodos.value.find { todo -> todo.todoId == todoId }
        return flowOf(todo)
    }
}