@file:OptIn(ExperimentalCoroutinesApi::class)

package com.githukudenis.todoey.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.githukudenis.todoey.data.local.Priority
import com.githukudenis.todoey.data.local.TodoEntity
import com.githukudenis.todoey.data.local.TodoeyDatabase
import com.githukudenis.todoey.data.local.TodosDao
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class TodoeyDaoTest {

    private lateinit var todoeyDatabase: TodoeyDatabase
    private lateinit var todoeyDao: TodosDao

    @Before
    fun setUp() {
        todoeyDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TodoeyDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        todoeyDao = todoeyDatabase.todosDao()
    }

    @After
    fun tearDown() {
        todoeyDatabase.close()
    }

    @Test
    fun insertTodo() = runTest(StandardTestDispatcher()) {
        val todo = TodoEntity(
            todoId = 10,
            todoTitle = "Some test title",
            todoDescription = "Some dummy desc",
            todoDueTime = 0L,
            todoDueDate = 0L,
            completed = false,
            priority = Priority.HIGH
        )

        todoeyDao.insertTodo(todo)

        val todos = todoeyDao.getAllTodos()
        assertThat(todos).contains(todo)
    }

    @Test
    fun deleteTodo() = runTest(StandardTestDispatcher()) {
        val todo1 = TodoEntity(
            todoId = 3,
            todoTitle = "Some title",
            todoDescription = "Some description",
            todoDueDate = 0L,
            todoDueTime = 0L
        )
        val todo2 = TodoEntity(
            todoId = 4,
            todoTitle = "Some title",
            todoDescription = "Some description",
            todoDueDate = 0L,
            todoDueTime = 0L
        )
        val todo3 = TodoEntity(
            todoId = 6,
            todoTitle = "Some title",
            todoDescription = "Some description",
            todoDueDate = 0L,
            todoDueTime = 0L
        )

        todoeyDao.insertTodo(todo1)
        todoeyDao.insertTodo(todo2)
        todoeyDao.insertTodo(todo3)

        todoeyDao.deleteTodo(todo2.todoId ?: return@runTest)
        val todos = todoeyDao.getAllTodos()
        assertThat(todos).doesNotContain(todo2)
    }

    @Test
    fun getAllTodos() = runTest(StandardTestDispatcher()) {
        val todo1 = TodoEntity(
            todoId = 3,
            todoTitle = "Some title",
            todoDescription = "Some description",
            todoDueDate = 0L,
            todoDueTime = 0L
        )
        val todo2 = TodoEntity(
            todoId = 4,
            todoTitle = "Some title",
            todoDescription = "Some description",
            todoDueDate = 0L,
            todoDueTime = 0L
        )
        val todo3 = TodoEntity(
            todoId = 6,
            todoTitle = "Some title",
            todoDescription = "Some description",
            todoDueDate = 0L,
            todoDueTime = 0L
        )

        todoeyDao.insertTodo(todo1)
        todoeyDao.insertTodo(todo2)
        todoeyDao.insertTodo(todo3)

        val allTodos = todoeyDao.getAllTodos()
        assertThat(allTodos).hasSize(3)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getTodoById() = runTest(StandardTestDispatcher()) {
        val todo1 = TodoEntity(
            todoId = 3,
            todoTitle = "Some title",
            todoDescription = "Some description",
            todoDueDate = 0L,
            todoDueTime = 0L
        )
        val todo2 = TodoEntity(
            todoId = 4,
            todoTitle = "Some title",
            todoDescription = "Some description",
            todoDueDate = 0L,
            todoDueTime = 0L
        )
        val todo3 = TodoEntity(
            todoId = 6,
            todoTitle = "Some title",
            todoDescription = "Some description",
            todoDueDate = 0L,
            todoDueTime = 0L
        )

        todoeyDao.insertTodo(todo1)
        todoeyDao.insertTodo(todo2)
        todoeyDao.insertTodo(todo3)

        val todoById = todoeyDao.getTodoById(todo3.todoId ?: return@runTest)
        assertThat(todoById).isEqualTo(todo3)
    }
}