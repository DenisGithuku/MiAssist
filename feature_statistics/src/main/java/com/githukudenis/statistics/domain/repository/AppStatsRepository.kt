package com.githukudenis.statistics.domain.repository

import android.app.usage.UsageStats
import kotlinx.coroutines.flow.Flow

interface AppStatsRepository {

    suspend fun getUsageStats(): Flow<List<UsageStats>>
}