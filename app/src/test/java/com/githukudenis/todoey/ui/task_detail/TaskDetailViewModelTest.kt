package com.githukudenis.todoey.ui.task_detail

import androidx.lifecycle.SavedStateHandle
import com.githukudenis.todoey.data.FakeTasksRepository
import com.githukudenis.todoey.data.local.TaskEntity
import com.githukudenis.todoey.util.MainCoroutineRule
import com.githukudenis.todoey.util.OrderType
import com.githukudenis.todoey.util.SortType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
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
        val task = TaskEntity(taskId = 40, taskTitle = "", taskDescription = "")
        tasksRepository.addTask(task)
        taskDetailViewModel.getTaskById(40)
        val newTask = taskDetailViewModel.state.first().taskDetail!!.copy(taskTitle = "New Title", taskDescription = "New description")
        val event = TaskDetailEvent.UpdateTask(newTask)
        taskDetailViewModel.onEvent(event)
        val tasks = tasksRepository.getAllTasks(SortType.DUE_DATE, OrderType.ASCENDING).first()
        assertTrue(tasks.any { it == newTask })
    }

    @Test
    fun getTaskById() = runTest {
        val task = TaskEntity(taskId = 12, taskTitle = "")
        tasksRepository.addTask(task)
        val taskFromRepo = tasksRepository.getTaskById(task.taskId ?: return@runTest).first()
        assertEquals(task, taskFromRepo)
    }

    @Test
    fun deleteTask() = runTest {
        val task = TaskEntity(taskId = 15, taskTitle = "")
        tasksRepository.addTask(task)
        val allTasks = tasksRepository.getAllTasks(sortType = SortType.DUE_DATE, orderType = OrderType.DESCENDING).first()
        assert(allTasks.contains(task))

        taskDetailViewModel.deleteTask(task)
        assert(!allTasks.contains(task))
    }
}