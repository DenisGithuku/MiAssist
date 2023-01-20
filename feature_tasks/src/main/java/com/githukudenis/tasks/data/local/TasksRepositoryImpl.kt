package com.githukudenis.tasks.data.local

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.content.getSystemService
import com.denisgithuku.tasks.data.local.TaskEntity
import com.githukudenis.tasks.domain.TasksRepository
import com.githukudenis.tasks.ui.AlarmReceiver
import com.githukudenis.tasks.util.OrderType
import com.githukudenis.tasks.util.SortType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor(
    private val tasksDataSource: TasksDataSource,
    private val context: Context
) : TasksRepository {
    override suspend fun addTask(taskEntity: TaskEntity) {
        return tasksDataSource.addTask(taskEntity)
    }

    override suspend fun getAllTasks(sortType: SortType, orderType: OrderType): Flow<List<TaskEntity>> {
        return tasksDataSource.getAllTasks(sortType = sortType, orderType = orderType)
    }

    override suspend fun deleteTask(taskEntity: TaskEntity) {
        tasksDataSource.deleteTask(taskEntity)
    }

    override suspend fun getTaskById(todoId: Long): Flow<TaskEntity?> {
        return tasksDataSource.getTaskById(todoId)
    }

    override suspend fun updateTask(
        taskEntity: TaskEntity
    ) {
        return tasksDataSource.updateTask(
            taskEntity
        )
    }

    override suspend fun setTaskReminder(
        alarmTime: Long,
        taskTitle: String
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("Message", taskTitle)
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            alarmTime,
            PendingIntent.getBroadcast(context, Math.random().toInt(), intent, PendingIntent.FLAG_IMMUTABLE)
        )
    }
}