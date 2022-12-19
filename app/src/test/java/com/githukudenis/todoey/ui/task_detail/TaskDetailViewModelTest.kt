package com.githukudenis.todoey.ui.task_detail

import androidx.lifecycle.SavedStateHandle
import com.githukudenis.todoey.data.FakeTasksRepository
import com.githukudenis.todoey.data.local.TaskEntity
import com.githukudenis.todoey.util.MainCoroutineRule
import com.githukudenis.todoey.util.OrderType
import com.githukudenis.todoey.util.SortType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TaskDetailViewModelTest {

    @get:Rule
    val mainCoroutineRule by lazy { MainCoroutineRule() }

    private lateinit var tasksRepository: FakeTasksRepository
    private lateinit var taskDetailViewModel: TaskDetailViewModel

    @Before
    fun setUp() {
        tasksRepository = FakeTasksRepository()
        taskDetailViewModel = TaskDetailViewModel(tasksRepository, SavedStateHandle())
    }

    @Test
    fun onEvent() = runTest {
        val task = TaskEntity(taskTitle = "")
        val event = TaskDetailEvent.SaveTask(task)
        taskDetailViewModel.onEvent(event)
        val tasks = tasksRepository.getAllTasks(SortType.DUE_DATE, OrderType.ASCENDING).first()
        assertTrue(tasks.any { it == task })
    }

    @Test
    fun getTaskById() = runTest {
        val task = TaskEntity(taskId = 12, taskTitle = "")
        tasksRepository.addTask(task)
        val taskFromRepo = tasksRepository.getTaskById(task.taskId ?: return@runTest).first()
        assertEquals(task, taskFromRepo)
    }
}