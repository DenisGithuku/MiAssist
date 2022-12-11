package com.githukudenis.todoey.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos_table")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val todoId: Long? = null,
    val todoTitle: String,
    val todoDescription: String? = null,
    val todoDueTime: Long? = null,
    val todoDueDate: Long? = null,
    val completed: Boolean = false,
    val priority: Priority = Priority.LOW
)
