package com.githukudenis.todoey.di

import android.content.Context
import androidx.room.Room
import com.githukudenis.todoey.data.local.TodoeyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TodoeyAppTestModule {

    @Named("test_db")
    @Provides
    fun provideTodoeyTestDatabase(
        @ApplicationContext context: Context
    ): TodoeyDatabase {
        return Room.inMemoryDatabaseBuilder(
            context,
            TodoeyDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }
}