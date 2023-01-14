package com.githukudenis.tasks.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.denisgithuku.tasks.data.local.Converters
import com.denisgithuku.tasks.data.local.TaskEntity

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TasksDatabase : RoomDatabase() {
    abstract fun tasksDao(): TasksDao
}
