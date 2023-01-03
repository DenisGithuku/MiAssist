package com.githukudenis.statistics.data

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import com.githukudenis.statistics.domain.repository.AppStatsRepository
import com.githukudenis.statistics.util.hasUsagePermissions
import java.util.Calendar

class AppUsageProvider(
    private val context: Context
) : AppStatsRepository {
    override suspend fun getUsageStats(): List<UsageStats> {
        if (!context.hasUsagePermissions()) {
            throw Exception(message = "This app needs permissions to access app usage.")
        }

        val usageStatsManager =
            context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val calendar = Calendar.getInstance()
        calendar.add(
            Calendar.HOUR_OF_DAY,
            -Calendar.HOUR_OF_DAY
        )
        return usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            calendar.timeInMillis,
            System.currentTimeMillis()
        )
    }
}