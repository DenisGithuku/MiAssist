package com.githukudenis.todoey.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TodoEntity::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TodoeyDatabase : RoomDatabase() {
    abstract fun todosDao(): TodosDao
}
