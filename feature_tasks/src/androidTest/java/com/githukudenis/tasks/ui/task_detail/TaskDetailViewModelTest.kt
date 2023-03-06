package com.githukudenis.tasks.ui.task_detail

import androidx.lifecycle.SavedStateHandle
import com.denisgithuku.tasks.data.local.TaskEntity
import com.githukudenis.tasks.data.FakeTasksRepository
import com.githukudenis.tasks.util.OrderType
import com.githukudenis.tasks.util.SortType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TaskDetailViewModelTest {

    private lateinit var tasksRepository: FakeTasksRepository
    private lateinit var taskDetailViewModel: TaskDetailViewModel

    @Before
    fun setUp() {
        tasksRepository = FakeTasksRepository()
        taskDetailViewModel = TaskDetailViewModel(
            tasksRepository,
            SavedStateHandle()
        )
    }

    @Test
    fun onEvent() = runTest {
        var tasks = emptyList<TaskEntity>()
        var newTask: TaskEntity? = null
        val task = TaskEntity(taskId = 40, taskTitle = "", taskDescription = "")
        tasksRepository.addTask(task)
        taskDetailViewModel.getTaskById(40)
        val job = launch(UnconfinedTestDispatcher(scheduler = TestCoroutineScheduler())) {
            newTask = taskDetailViewModel.state.first().taskDetail!!.copy(
                taskTitle = "New Title",
                taskDescription = "New description"
            )
            val event = TaskDetailEvent.UpdateTask(newTask ?: return@launch)
            taskDetailViewModel.onEvent(event)
            tasks = tasksRepository.getAllTasks(SortType.DUE_DATE, OrderType.ASCENDING).first()
        }
        job.cancel()
        assertTrue(tasks.any { it == newTask })
    }

    @Test
    fun getTaskById() = runTest {
        var taskFromRepo: TaskEntity? = TaskEntity(taskTitle = "")
        val task = TaskEntity(taskId = 12, taskTitle = "")
        tasksRepository.addTask(task)
        val job = launch(UnconfinedTestDispatcher(scheduler = TestCoroutineScheduler())) {
            taskFromRepo = tasksRepository.getTaskById(task.taskId ?: return@launch).first()
        }
        job.cancel()
        assertEquals(task, taskFromRepo)
    }

    @Test
    fun deleteTask() = runTest {
        var allTasks = emptyList<TaskEntity>()
        val task = TaskEntity(taskId = 15, taskTitle = "")
        tasksRepository.addTask(task)
        taskDetailViewModel.deleteTask(task)

        val job = launch(UnconfinedTestDispatcher(scheduler = TestCoroutineScheduler())) {
            allTasks = tasksRepository.getAllTasks(
                sortType = SortType.DUE_DATE,
                orderType = OrderType.DESCENDING
            ).first()
        }
        job.cancel()
        assert(!allTasks.contains(task))
    }
}