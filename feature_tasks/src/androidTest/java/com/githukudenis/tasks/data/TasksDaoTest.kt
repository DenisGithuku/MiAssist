@file:OptIn(ExperimentalCoroutinesApi::class)

package com.githukudenis.miassist.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.denisgithuku.tasks.data.local.Priority
import com.denisgithuku.tasks.data.local.TaskEntity
import com.githukudenis.tasks.data.local.TasksDao
import com.githukudenis.tasks.data.local.TasksDatabase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.time.LocalTime

@RunWith(AndroidJUnit4::class)
@SmallTest
class TasksDaoTest {

    private lateinit var tasksDatabase: TasksDatabase
    private lateinit var tasksDao: TasksDao
    val testDispatcher = StandardTestDispatcher()
    val scope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        tasksDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TasksDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        tasksDao = tasksDatabase.tasksDao()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        tasksDatabase.close()
    }

    @Test
    fun insertTask() = scope.runTest {
        val task = TaskEntity(
            taskId = 10,
            taskTitle = "Some test title",
            taskDescription = "Some dummy desc",
            taskDueTime = LocalTime.now(),
            taskDueDate = LocalDate.now(),
            completed = false,
            priority = Priority.HIGH
        )

        tasksDao.insertTask(task)

        val tasks = tasksDao.getAllTasks()
        assertThat(tasks).contains(task)
    }

    @Test
    fun deleteTodo() = scope.runTest {
        val task1 = TaskEntity(
            taskId = 3,
            taskTitle = "Some title",
            taskDescription = "Some description",
            taskDueTime = LocalTime.now(),
            taskDueDate = LocalDate.now()
        )
        val task2 = TaskEntity(
            taskId = 4,
            taskTitle = "Some title",
            taskDescription = "Some description",
            taskDueTime = LocalTime.now(),
            taskDueDate = LocalDate.now()
        )
        val task3 = TaskEntity(
            taskId = 6,
            taskTitle = "Some title",
            taskDescription = "Some description",
            taskDueTime = LocalTime.now(),
            taskDueDate = LocalDate.now()
        )

        tasksDao.insertTask(task1)
        tasksDao.insertTask(task2)
        tasksDao.insertTask(task3)

        tasksDao.deleteTask(task2.taskId ?: return@runTest)
        val tasks = tasksDao.getAllTasks()
        assertThat(tasks).doesNotContain(task2)
    }

    @Test
    fun getAllTasks() = scope.runTest {
        val task1 = TaskEntity(
            taskId = 3,
            taskTitle = "Some title",
            taskDescription = "Some description",
            taskDueTime = LocalTime.now(),
            taskDueDate = LocalDate.now()
        )
        val task2 = TaskEntity(
            taskId = 4,
            taskTitle = "Some title",
            taskDescription = "Some description",
            taskDueTime = LocalTime.now(),
            taskDueDate = LocalDate.now()
        )
        val task3 = TaskEntity(
            taskId = 6,
            taskTitle = "Some title",
            taskDescription = "Some description",
            taskDueTime = LocalTime.now(),
            taskDueDate = LocalDate.now()
        )

        tasksDao.insertTask(task1)
        tasksDao.insertTask(task2)
        tasksDao.insertTask(task3)

        val allTodos = tasksDao.getAllTasks()
        assertThat(allTodos).hasSize(3)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getTodoById() = scope.runTest {
        val task1 = TaskEntity(
            taskId = 3,
            taskTitle = "Some title",
            taskDescription = "Some description",
            taskDueTime = LocalTime.now(),
            taskDueDate = LocalDate.now()
        )
        val task2 = TaskEntity(
            taskId = 4,
            taskTitle = "Some title",
            taskDescription = "Some description",
            taskDueTime = LocalTime.now(),
            taskDueDate = LocalDate.now()
        )
        val task3 = TaskEntity(
            taskId = 6,
            taskTitle = "Some title",
            taskDescription = "Some description",
            taskDueTime = LocalTime.now(),
            taskDueDate = LocalDate.now()
        )

        tasksDao.insertTask(task1)
        tasksDao.insertTask(task2)
        tasksDao.insertTask(task3)

        val taskById = tasksDao.getTaskById(task3.taskId ?: return@runTest)
        assertThat(taskById).isEqualTo(task3)
    }

    @Test
    fun updateTask() = runTest {
        val task = TaskEntity(
            taskId = 3,
            taskTitle = "Some title",
            taskDescription = "Some description",
            taskDueTime = LocalTime.now(),
            taskDueDate = LocalDate.now()
        )
        tasksDao.insertTask(task)
        assertThat(tasksDao.getAllTasks().first().completed).isFalse()

        val newTask = task.copy(taskTitle = "New title", taskDescription = "New description")

        tasksDao.updateTask(
            completed = newTask.completed,
            taskTitle = newTask.taskTitle,
            taskDescription = newTask.taskDescription ?: return@runTest,
            taskDueDate = newTask.taskDueDate ?: return@runTest,
            taskDueTime = newTask.taskDueTime ?: return@runTest,
            priority = newTask.priority,
            taskId = newTask.taskId ?: return@runTest
        )

        assertThat(tasksDao.getTaskById(task.taskId!!) != task)
    }
}