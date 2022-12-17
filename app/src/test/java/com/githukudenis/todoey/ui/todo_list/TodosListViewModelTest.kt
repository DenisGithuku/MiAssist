package com.githukudenis.todoey.ui.todo_list

import com.githukudenis.todoey.data.FakeTodoRepository
import com.githukudenis.todoey.data.local.Priority
import com.githukudenis.todoey.data.local.TodoEntity
import com.githukudenis.todoey.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TodosListViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var testRepository: FakeTodoRepository
    private lateinit var todosListViewModel: TodosListViewModel

    @Before
    fun setUp() {
        testRepository = FakeTodoRepository()
        todosListViewModel = TodosListViewModel(testRepository)
    }

    @Test
    fun getAllTodos() = runTest {
        assertEquals(0, todosListViewModel.state.value.todos.size)

        testRepository.addTodo(TodoEntity(todoTitle = ""))
        testRepository.addTodo(TodoEntity(todoTitle = ""))
        testRepository.addTodo(TodoEntity(todoTitle = ""))

        todosListViewModel.getAllTodos()

        assertEquals(3, todosListViewModel.state.value.todos.size)
    }

    @Test
    fun onEvent() = runTest {
        val event = TodoListEvent.ChangePriorityFilter(Priority.HIGH)
        todosListViewModel.onEvent(event)
        assertEquals(Priority.HIGH, todosListViewModel.state.value.selectedPriority)
    }

    @Test
    fun filterTodosByPriority() = runTest {
        testRepository.addTodo(TodoEntity(todoTitle = ""))
        testRepository.addTodo(TodoEntity(todoTitle = ""))
        testRepository.addTodo(TodoEntity(todoTitle = ""))

        todosListViewModel.changePriority(priority = Priority.LOW)
        val firstTodo = todosListViewModel.state.value.todos.first()
        assertEquals(Priority.LOW, firstTodo.priority)
    }

    @Test
    fun changePriorityUpdatesSelectedPriority() = runTest {
        testRepository.addTodo(TodoEntity(todoTitle = ""))
        testRepository.addTodo(TodoEntity(todoTitle = ""))
        testRepository.addTodo(TodoEntity(todoTitle = ""))

        todosListViewModel.changePriority(priority = Priority.MODERATE)
        assertEquals(Priority.MODERATE, todosListViewModel.state.value.selectedPriority)
    }
}