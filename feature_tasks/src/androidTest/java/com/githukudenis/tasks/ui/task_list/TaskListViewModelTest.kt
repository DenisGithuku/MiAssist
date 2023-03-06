package com.githukudenis.tasks.ui.task_list

import com.denisgithuku.tasks.data.local.Priority
import com.denisgithuku.tasks.data.local.TaskEntity
import com.githukudenis.tasks.data.FakeTasksRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TaskListViewModelTest {

    private lateinit var testRepository: FakeTasksRepository
    private lateinit var taskListViewModel: TaskListViewModel

    @Before
    fun setUp() {
        testRepository = FakeTasksRepository()
        taskListViewModel = TaskListViewModel(testRepository)
    }

    @Test
    fun getAllTasks() = runTest {
        assertEquals(0, taskListViewModel.state.value.tasks.size)

        testRepository.addTask(TaskEntity(taskTitle = "", priority = Priority.HIGH))
        testRepository.addTask(TaskEntity(taskTitle = "", priority = Priority.HIGH))
        testRepository.addTask(TaskEntity(taskTitle = "", priority = Priority.HIGH))

        val job = launch(UnconfinedTestDispatcher(scheduler = TestCoroutineScheduler())) {
            taskListViewModel.getAllTodos()
        }
        job.cancel()

        assertEquals(3, taskListViewModel.state.value.tasks.size)
    }

    @Test
    fun onEvent() = runTest {
        val event = TaskListEvent.ChangePriorityFilter(Priority.HIGH)
        taskListViewModel.onEvent(event)
        assertEquals(Priority.HIGH, taskListViewModel.state.value.selectedPriority)
    }

    @Test
    fun filterTasksByPriority() = runTest {
        var firstTask = TaskEntity(taskTitle = "")
        testRepository.addTask(TaskEntity(taskTitle = ""))
        testRepository.addTask(TaskEntity(taskTitle = ""))
        testRepository.addTask(TaskEntity(taskTitle = ""))

        taskListViewModel.onEvent(TaskListEvent.ChangePriorityFilter(priority = Priority.LOW))

        val job = launch(UnconfinedTestDispatcher(scheduler = TestCoroutineScheduler())) {
            firstTask = taskListViewModel.state.value.tasks.first()
        }
        job.cancel()
        assertEquals(Priority.LOW, firstTask.priority)
    }

    @Test
    fun toggleCompleteTask() = runTest {
        val testTask =
            TaskEntity(taskId = 45, taskTitle = "", completed = true, priority = Priority.HIGH)
        testRepository.addTask(testTask)
        testRepository.updateTask(testTask.copy(completed = !testTask.completed))
        val task = testRepository.getTaskById(todoId = testTask.taskId ?: return@runTest)
        assertEquals(false, task.first()?.completed)
    }
}