package com.githukudenis.todoey.di

import android.content.Context
import androidx.room.Room
import com.githukudenis.todoey.data.local.TodoRepositoryImpl
import com.githukudenis.todoey.data.local.TodoeyDatabase
import com.githukudenis.todoey.data.local.TodosDao
import com.githukudenis.todoey.data.local.TodosDataSource
import com.githukudenis.todoey.domain.TodosRepository
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

    @Provides
    @Singleton
    fun provideTodosDataSource(todosDao: TodosDao): TodosDataSource = TodosDataSource(todosDao)

    @Provides
    @Singleton
    fun provideTodosRepository(
        todosDataSource: TodosDataSource
    ): TodosRepository = TodoRepositoryImpl(todosDataSource = todosDataSource)
}