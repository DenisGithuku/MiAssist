package com.githukudenis.statistics.domain.repository

import com.githukudenis.statistics.domain.model.AppUsageStatsInfo
import kotlinx.coroutines.flow.Flow

interface AppStatsRepository {

    suspend fun getUsageStats(): Flow<List<AppUsageStatsInfo>>
}