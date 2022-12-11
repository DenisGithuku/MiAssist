package com.githukudenis.todoey.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TodosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todoEntity: TodoEntity)

    @Query("SELECT * FROM todos_table WHERE todoId LIKE :todoId")
    suspend fun getTodoById(todoId: Long): TodoEntity

    @Query("DELETE FROM todos_table WHERE todoId LIKE :todoId")
    suspend fun deleteTodo(todoId: Long)

    @Query("SELECT * FROM todos_table ORDER BY todoId DESC")
    suspend fun getAllTodos(): List<TodoEntity>
}