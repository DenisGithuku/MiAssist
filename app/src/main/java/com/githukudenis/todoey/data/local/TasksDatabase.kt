package com.githukudenis.todoey.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TasksDatabase : RoomDatabase() {
    abstract fun tasksDao(): TasksDao
}
