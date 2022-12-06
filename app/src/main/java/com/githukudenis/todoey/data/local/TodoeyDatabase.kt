package com.githukudenis.todoey.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [], version = 1, exportSchema = false)
abstract class TodoeyDatabase: RoomDatabase() {
    abstract fun todosDao(): TodosDao
}