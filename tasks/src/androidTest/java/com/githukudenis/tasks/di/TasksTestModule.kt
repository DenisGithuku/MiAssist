package com.githukudenis.todoey.di

import android.content.Context
import androidx.room.Room
import com.githukudenis.todoey.data.local.TasksDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TasksTestModule {

    @Named("test_db")
    @Provides
    fun provideTasksTestDatabase(
        @ApplicationContext context: Context
    ): TasksDatabase {
        return Room.inMemoryDatabaseBuilder(
            context,
            TasksDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }
}