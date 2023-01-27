package com.githukudenis.tasks.data.local

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.AlarmManagerCompat
import com.denisgithuku.tasks.data.local.TaskEntity
import com.githukudenis.tasks.domain.TasksRepository
import com.githukudenis.tasks.ui.AlarmReceiver
import com.githukudenis.tasks.util.OrderType
import com.githukudenis.tasks.util.SortType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

private const val TAG = "TasksDataSourceError"

class TasksDataSource @Inject constructor(
    private val tasksDao: TasksDao,
    private val context: Context
) : TasksRepository {
    override suspend fun addTask(taskEntity: TaskEntity) {
        return tasksDao.insertTask(taskEntity)
    }

    override suspend fun getAllTasks(
        sortType: SortType,
        orderType: OrderType
    ): Flow<List<TaskEntity>> = flow {
        try {
            val todos = tasksDao.getAllTasks()
            when (orderType) {
                OrderType.ASCENDING -> {
                    when (sortType) {
                        SortType.DUE_DATE -> {
                            val sortedTodos = todos.sortedBy { todo -> todo.taskDueDate }
                            emit(sortedTodos)
                        }

                        SortType.DUE_TIME -> {
                            val sortedTodos = todos.sortedBy { todo -> todo.taskDueTime }
                            emit(sortedTodos)
                        }
                    }
                }

                OrderType.DESCENDING -> {
                    when (sortType) {
                        SortType.DUE_DATE -> {
                            val sortedTodos = todos.sortedByDescending { todo -> todo.taskDueDate }
                            emit(sortedTodos)
                        }

                        SortType.DUE_TIME -> {
                            val sortedTodos = todos.sortedByDescending { todo -> todo.taskDueTime }
                            emit(sortedTodos)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "getAllTodos: ${e.localizedMessage}")
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteTask(taskEntity: TaskEntity) {
        try {
            tasksDao.deleteTask(taskEntity.taskId ?: return)
        } catch (e: Exception) {
            Log.e(TAG, "deleteTodo: ${e.localizedMessage}")
        }
    }

    override suspend fun getTaskById(todoId: Long): Flow<TaskEntity?> = flow {
        try {
            val todo = tasksDao.getTaskById(todoId)
            emit(todo)
        } catch (e: Exception) {
            Log.e(TAG, "getTodoById: ${e.message}")
            emit(null)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun updateTask(
        taskEntity: TaskEntity
    ) {
        try {
            tasksDao.updateTask(
                taskTitle = taskEntity.taskTitle,
                taskDescription = taskEntity.taskDescription ?: return,
                taskDueDate = taskEntity.taskDueDate ?: return,
                taskDueTime = taskEntity.taskDueTime ?: return,
                completed = taskEntity.completed,
                priority = taskEntity.priority,
                taskId = taskEntity.taskId ?: return

            )
        } catch (e: Exception) {
            Log.e(TAG, "toggleCompleteTask: ${e.localizedMessage}")
        }
    }

    override suspend fun setTaskReminder(alarmTime: Long, taskTitle: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("Message", taskTitle)
        }

        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        AlarmManagerCompat.setExactAndAllowWhileIdle(
            alarmManager,
            AlarmManager.RTC_WAKEUP,
            alarmTime,
            pendingIntent
        )
    }
}