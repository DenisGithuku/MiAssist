package com.githukudenis.todoey.di

import android.content.Context
import androidx.room.Room
import com.githukudenis.todoey.data.local.TodoeyDatabase
import com.githukudenis.todoey.data.local.TodosDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoeyDatabase(@ApplicationContext context: Context): TodoeyDatabase {
        return Room.databaseBuilder(
            context,
            TodoeyDatabase::class.java,
            "todoey_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideTodosDao(todoeyDatabase: TodoeyDatabase): TodosDao {
        return todoeyDatabase.todosDao()
    }
}