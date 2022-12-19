package com.githukudenis.todoey.ui.task_list

import com.githukudenis.todoey.data.FakeTasksRepository
import com.githukudenis.todoey.data.local.Priority
import com.githukudenis.todoey.data.local.TaskEntity
import com.githukudenis.todoey.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TaskListViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var testRepository: FakeTasksRepository
    private lateinit var taskListViewModel: TaskListViewModel

    @Before
    fun setUp() {
        testRepository = FakeTasksRepository()
        taskListViewModel = TaskListViewModel(testRepository)
    }

    @Test
    fun getAllTasks() = runTest {
        assertEquals(0, taskListViewModel.state.value.todos.size)

        testRepository.addTask(TaskEntity(taskTitle = ""))
        testRepository.addTask(TaskEntity(taskTitle = ""))
        testRepository.addTask(TaskEntity(taskTitle = ""))

        taskListViewModel.getAllTodos()

        assertEquals(3, taskListViewModel.state.value.todos.size)
    }

    @Test
    fun onEvent() = runTest {
        val event = TaskListEvent.ChangePriorityFilter(Priority.HIGH)
        taskListViewModel.onEvent(event)
        assertEquals(Priority.HIGH, taskListViewModel.state.value.selectedPriority)
    }

    @Test
    fun filterTasksByPriority() = runTest {
        testRepository.addTask(TaskEntity(taskTitle = ""))
        testRepository.addTask(TaskEntity(taskTitle = ""))
        testRepository.addTask(TaskEntity(taskTitle = ""))

        taskListViewModel.changePriority(priority = Priority.LOW)
        val firstTodo = taskListViewModel.state.value.todos.first()
        assertEquals(Priority.LOW, firstTodo.priority)
    }

    @Test
    fun changePriorityUpdatesSelectedPriority() = runTest {
        testRepository.addTask(TaskEntity(taskTitle = ""))
        testRepository.addTask(TaskEntity(taskTitle = ""))
        testRepository.addTask(TaskEntity(taskTitle = ""))

        taskListViewModel.changePriority(priority = Priority.MODERATE)
        assertEquals(Priority.MODERATE, taskListViewModel.state.value.selectedPriority)
    }
}