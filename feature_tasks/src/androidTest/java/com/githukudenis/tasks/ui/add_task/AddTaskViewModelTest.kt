package com.githukudenis.miassist.ui.add_task

import com.githukudenis.tasks.ui.add_task.AddTaskViewModel
import com.githukudenis.miassist.data.FakeTasksRepository
import com.githukudenis.miassist.data.local.TaskEntity
import com.githukudenis.miassist.util.MainCoroutineRule
import com.githukudenis.miassist.util.OrderType
import com.githukudenis.miassist.util.SortType
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

        addTaskViewModel.saveTask(todo)
        val todos = todoRepo.getAllTasks(SortType.DUE_DATE, OrderType.ASCENDING).first()

        assertThat(todos).contains(todo)
    }

    @Test
    fun todoAddedCompleteIsTrue() = runTest {
        val todoRepo = FakeTasksRepository()
        val addTaskViewModel = AddTaskViewModel(todoRepo)
        val todo = TaskEntity(taskId = 10, taskTitle = "")

        addTaskViewModel.saveTask(todo)
        val viewModelState = addTaskViewModel.state.value

        assertEquals(viewModelState.todoAdded, true)
    }
}