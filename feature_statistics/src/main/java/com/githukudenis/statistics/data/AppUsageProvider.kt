package com.githukudenis.statistics.data

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.githukudenis.statistics.util.hasUsagePermissions
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.Calendar

class AppUsageProvider(
    private val context: Context
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getUsageStats(): List<UsageStats> {
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
        return usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startOfDay,
            System.currentTimeMillis()
        )
    }
}