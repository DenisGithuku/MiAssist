
package com.githukudenis.tasks.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.denisgithuku.tasks.data.local.Priority
import com.denisgithuku.tasks.data.local.TaskEntity
import java.time.LocalDate
import java.time.LocalTime

@Dao
interface TasksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(taskEntity: TaskEntity)

    @Query("SELECT * FROM todos_table WHERE taskId LIKE :taskId")
    suspend fun getTaskById(taskId: Long): TaskEntity

    @Query("DELETE FROM todos_table WHERE taskId LIKE :taskId")
    suspend fun deleteTask(taskId: Long)

    @Query("SELECT * FROM todos_table ORDER BY taskId DESC")
    suspend fun getAllTasks(): List<TaskEntity>

    @Query(
        "UPDATE todos_table SET taskTitle = :taskTitle," +
            " taskDescription = :taskDescription," +
            " completed = :completed," +
            " taskDueDate = :taskDueDate, " +
            " taskDueTime = :taskDueTime, " +
            " priority = :priority " +
            " WHERE taskId LIKE :taskId"
    )
    suspend fun updateTask(
        taskTitle: String,
        taskDescription: String,
        taskDueDate: LocalDate,
        taskDueTime: LocalTime,
        completed: Boolean,
        priority: Priority,
        taskId: Long
    )
}