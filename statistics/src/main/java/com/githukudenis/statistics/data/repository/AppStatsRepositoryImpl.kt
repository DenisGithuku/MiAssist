package com.githukudenis.statistics.data.repository

import android.app.usage.UsageStats
import android.os.Build
import androidx.annotation.RequiresApi
import com.githukudenis.statistics.data.AppUsageProvider
import com.githukudenis.statistics.domain.repository.AppStatsRepository

class AppStatsRepositoryImpl(
    private val appUsageProvider: AppUsageProvider
): AppStatsRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getUsageStats(): List<UsageStats> {
        return appUsageProvider.getUsageStats()
    }
}