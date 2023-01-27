package com.githukudenis.tasks.ui.add_task

import com.denisgithuku.tasks.data.local.TaskEntity
import com.githukudenis.tasks.data.FakeTasksRepository
import com.githukudenis.tasks.util.MainCoroutineRule
import com.githukudenis.tasks.util.OrderType
import com.githukudenis.tasks.util.SortType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddTaskViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun addTodo() = runTest {
        val todoRepo = FakeTasksRepository()
        val addTaskViewModel = AddTaskViewModel(todoRepo)
        val todo = TaskEntity(taskId = 10, taskTitle = "")

        addTaskViewModel.onEvent(AddTaskEvent.SaveTask(todo))
        val todos = todoRepo.getAllTasks(SortType.DUE_DATE, OrderType.ASCENDING).first()

        assertThat(todos).contains(todo)
    }

    @Test
    fun todoAddedCompleteIsTrue() = runTest {
        val todoRepo = FakeTasksRepository()
        val addTaskViewModel = AddTaskViewModel(todoRepo)
        val todo = TaskEntity(taskId = 10, taskTitle = "")

        addTaskViewModel.onEvent(AddTaskEvent.SaveTask(todo))
        val viewModelState = addTaskViewModel.state.value

        assertEquals(viewModelState.todoAdded, true)
    }
}