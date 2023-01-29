package com.githukudenis.statistics.di

import android.content.Context
import com.githukudenis.statistics.data.repository.AppStatsRepositoryImpl
import com.githukudenis.statistics.domain.repository.AppStatsRepository
import com.githukudenis.statistics.util.ApplicationInfoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StatsModule {

    @Provides
    @Singleton
    fun provideAppStatsRepository(
        @ApplicationContext context: Context,
        applicationInfoMapper: ApplicationInfoMapper
    ): AppStatsRepository {
        return AppStatsRepositoryImpl(context, applicationInfoMapper)
    }

    @Provides
    @Singleton
    fun provideApplicationInfoMapper(@ApplicationContext context: Context): ApplicationInfoMapper {
        return ApplicationInfoMapper(context)
    }
}