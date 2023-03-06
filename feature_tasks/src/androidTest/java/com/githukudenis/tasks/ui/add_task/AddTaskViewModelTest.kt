package com.githukudenis.tasks.ui.add_task

import com.denisgithuku.tasks.data.local.TaskEntity
import com.githukudenis.tasks.data.FakeTasksRepository
import com.githukudenis.tasks.util.OrderType
import com.githukudenis.tasks.util.SortType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddTaskViewModelTest {

    @Test
    fun addTodo() = runTest(UnconfinedTestDispatcher()) {
        val todoRepo = FakeTasksRepository()
        val addTaskViewModel = AddTaskViewModel(todoRepo)
        val task = TaskEntity(taskId = 10, taskTitle = "")
        var tasks: List<TaskEntity> = emptyList()

        addTaskViewModel.onEvent(AddTaskEvent.SaveTask(task))
        val job = launch(UnconfinedTestDispatcher(scheduler = TestCoroutineScheduler())) {
            tasks = todoRepo.getAllTasks(SortType.DUE_DATE, OrderType.ASCENDING).first()
        }
        job.cancel()

        assertThat(tasks).contains(task)
    }

    @Test
    fun todoAddedCompleteIsTrue() = runTest {
        val todoRepo = FakeTasksRepository()
        val addTaskViewModel = AddTaskViewModel(todoRepo)
        val todo = TaskEntity(taskId = 10, taskTitle = "")
        var viewModelState = AddTaskUiState()

        addTaskViewModel.onEvent(AddTaskEvent.SaveTask(todo))
        val job = launch(UnconfinedTestDispatcher(scheduler = TestCoroutineScheduler())) {
            viewModelState = addTaskViewModel.state.value
        }

        job.cancel()
        assertEquals(viewModelState.todoAdded, true)
    }
}