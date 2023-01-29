package com.githukudenis.statistics.data.repository

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.githukudenis.statistics.domain.repository.AppStatsRepository
import com.githukudenis.statistics.util.hasUsagePermissions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

private const val TAG = "stats_list"
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
        /*
        Set the calendar time to midnight; start of day
         */
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        val startOfDay = calendar.timeInMillis
        val usageStatsList = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startOfDay,
            System.currentTimeMillis()
        )
        val installedApps = context.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

        /*
        Filter apps that do not belong to the system
         */
        val packageManager = context.packageManager
        val nonSystemApps = installedApps.filterNot { applicationInfo ->
            packageManager.getLaunchIntentForPackage(applicationInfo.packageName) == null
        }

        /*
        Only return stats for applications that do not belong to the system
         */
        val filteredStats = usageStatsList.filter { usageStats ->
            nonSystemApps.any { applicationInfo -> applicationInfo.packageName == usageStats.packageName }
        }
        /*
        Transform into application info data class
         */
//        val appInfoList = filteredStats.map { stats ->
//            AppUsageStatsInfo(
//                appName = installedApps.first { it.packageName == stats.packageName }.name,
//                packageName = stats.packageName,
//                lastTimeUsed = stats.lastTimeUsed,
//                totalTimeInForeground = stats.totalTimeInForeground
//            )
//        }
        Log.e(TAG, "getUsageStats: ${nonSystemApps.map { packageManager.getApplicationLabel(it) }}")
//        Log.e(TAG, "getUsageStats: ${appInfoList.map { it.appName }}")
        emit(filteredStats)
    }
}