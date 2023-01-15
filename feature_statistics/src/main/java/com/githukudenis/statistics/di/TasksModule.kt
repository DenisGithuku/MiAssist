package com.githukudenis.statistics.di

import android.content.Context
import com.githukudenis.statistics.data.AppUsageProvider
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
    fun provideAppUsageProvider(
        @ApplicationContext context: Context
    ): AppUsageProvider {
        return AppUsageProvider(context)
    }

    @Provides
    @Singleton
    fun provideAppStatsRepository(
        appUsageProvider: AppUsageProvider
    ): AppStatsRepository {
        return AppStatsRepositoryImpl(appUsageProvider)
    }
}