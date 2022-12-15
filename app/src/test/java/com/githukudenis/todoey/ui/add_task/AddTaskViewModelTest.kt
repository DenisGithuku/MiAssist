package com.githukudenis.todoey.ui.add_task

import com.githukudenis.todoey.data.FakeTodoRepository
import com.githukudenis.todoey.data.local.TodoEntity
import com.githukudenis.todoey.util.MainCoroutineRule
import com.githukudenis.todoey.util.OrderType
import com.githukudenis.todoey.util.SortType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddTaskViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun addTodo() = runTest(StandardTestDispatcher()) {
        val todoRepo = FakeTodoRepository()
        val addTaskViewModel = AddTaskViewModel(todoRepo)
        val todo = TodoEntity(todoId = 10, todoTitle = "")

        addTaskViewModel.saveTask(todo)
        val todos = todoRepo.getAllTodos(SortType.DUE_DATE, OrderType.ASCENDING).first()

        assertThat(todos).contains(todo)
    }

    @Test
    fun todoAddedCompleteIsTrue() = runTest(StandardTestDispatcher()) {
        val todoRepo = FakeTodoRepository()
        val addTaskViewModel = AddTaskViewModel(todoRepo)
        val todo = TodoEntity(todoId = 10, todoTitle = "")

        addTaskViewModel.saveTask(todo)
        val viewModelState = addTaskViewModel.state.value

        assertEquals(viewModelState.todoAdded, true)
    }
}