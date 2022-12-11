package com.githukudenis.todoey.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(entities = [TodoEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TodoeyDatabase : RoomDatabase() {
    abstract fun todosDao(): TodosDao
}

class Converters {
    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun stringToPriority(value: String): Priority {
        return Priority.valueOf(value)
    }
}