package com.githukudenis.todoey.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TasksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(taskEntity: TaskEntity)

    @Query("SELECT * FROM todos_table WHERE taskId LIKE :todoId")
    suspend fun getTaskById(todoId: Long): TaskEntity

    @Query("DELETE FROM todos_table WHERE taskId LIKE :todoId")
    suspend fun deleteTask(todoId: Long)

    @Query("SELECT * FROM todos_table ORDER BY taskId DESC")
    suspend fun getAllTasks(): List<TaskEntity>
}