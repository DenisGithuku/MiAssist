package com.githukudenis.todoey.ui.todo_list

import com.githukudenis.todoey.data.FakeTodoRepository
import com.githukudenis.todoey.data.local.TodoEntity
import com.githukudenis.todoey.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TodosListViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun getAllTodos() = runTest {
        val testRepository = FakeTodoRepository()
        val todosListViewModel: TodosListViewModel = TodosListViewModel(testRepository)

        assertEquals(0, todosListViewModel.state.value.size)

        testRepository.addTodo(TodoEntity(todoTitle = ""))
        testRepository.addTodo(TodoEntity(todoTitle = ""))
        testRepository.addTodo(TodoEntity(todoTitle = ""))

        todosListViewModel.getAllTodos()

        assertEquals(3, todosListViewModel.state.value.size)
    }
}