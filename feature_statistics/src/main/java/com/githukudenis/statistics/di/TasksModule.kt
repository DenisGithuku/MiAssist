package com.githukudenis.statistics.di

import android.content.Context
import com.githukudenis.statistics.data.repository.AppStatsRepositoryImpl
import com.githukudenis.statistics.domain.repository.AppStatsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TasksModule {

    @Provides
    @Singleton
    fun provideAppStatsRepository(
        @ApplicationContext context: Context
    ): AppStatsRepository {
        return AppStatsRepositoryImpl(context)
    }
}