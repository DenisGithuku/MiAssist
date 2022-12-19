package com.githukudenis.todoey.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "todos_table")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val taskId: Long? = null,
    val taskTitle: String,
    val taskDescription: String? = null,
    val taskDueTime: LocalTime? = null,
    val taskDueDate: LocalDate? = null,
    val completed: Boolean = false,
    val priority: Priority = Priority.LOW
)
