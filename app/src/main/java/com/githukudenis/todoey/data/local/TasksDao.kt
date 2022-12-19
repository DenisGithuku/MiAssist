package com.githukudenis.todoey.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

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

    @Query("UPDATE todos_table SET completed = :completed WHERE taskId LIKE :taskId")
    suspend fun toggleCompleteTask(completed: Boolean, taskId: Long)
}