package com.githukudenis.todoey.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "todos_table")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val todoId: Long? = null,
    val todoTitle: String,
    val todoDescription: String? = null,
    val todoDueTime: LocalTime? = null,
    val todoDueDate: LocalDate? = null,
    val completed: Boolean = false,
    val priority: Priority = Priority.LOW
)
