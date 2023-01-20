package com.githukudenis.statistics.domain.model

data class AppUsageStatsInfo(
    val appName: String,
    val packageName: String,
    val lastTimeUsed: Long,
    val totalTimeInForeground: Long
)
