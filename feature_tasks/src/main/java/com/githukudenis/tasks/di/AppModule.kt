package com.denisgithuku.tasks.di

import android.content.Context
import androidx.room.Room
import com.githukudenis.tasks.data.local.TasksRepositoryImpl
import com.githukudenis.tasks.data.local.TasksDao
import com.githukudenis.tasks.data.local.TasksDataSource
import com.githukudenis.tasks.data.local.TasksDatabase
import com.githukudenis.tasks.domain.TasksRepository
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
    fun provideTasksDatabase(@ApplicationContext context: Context): TasksDatabase {
        return Room.databaseBuilder(
            context,
            TasksDatabase::class.java,
            "tasks_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideTasksDao(tasksDatabase: TasksDatabase): TasksDao {
        return tasksDatabase.tasksDao()
    }

    @Provides
    @Singleton
    fun provideTasksDataSource(tasksDao: TasksDao): TasksDataSource = TasksDataSource(tasksDao)

    @Provides
    @Singleton
    fun provideTasksRepository(
        tasksDataSource: TasksDataSource,
        @ApplicationContext context: Context
    ): TasksRepository = TasksRepositoryImpl(tasksDataSource = tasksDataSource, context)
}