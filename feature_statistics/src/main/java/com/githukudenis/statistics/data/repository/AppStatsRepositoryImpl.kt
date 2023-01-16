package com.githukudenis.statistics.data.repository

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.githukudenis.statistics.domain.repository.AppStatsRepository
import com.githukudenis.statistics.util.hasUsagePermissions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.*

class AppStatsRepositoryImpl(
    private val context: Context
) : AppStatsRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getUsageStats(): Flow<List<UsageStats>> = flow {
        if (!context.hasUsagePermissions()) {
            throw Throwable(message = "This app needs permissions to access app usage.")
        }

        val usageStatsManager =
            context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val calendar = Calendar.getInstance()
        calendar.add(
            Calendar.HOUR_OF_DAY,
            -Calendar.HOUR_OF_DAY
        )
        val localDate = LocalDate.now()
        val startOfDay = localDate.atTime(
            LocalTime.MIN
        ).atZone(
            ZoneId.systemDefault()
        ).toInstant().toEpochMilli()
        val usageStats = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startOfDay,
            System.currentTimeMillis()
        )
        flowOf(usageStats)
    }
}