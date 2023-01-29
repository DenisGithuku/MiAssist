package com.githukudenis.statistics.domain.model

import android.graphics.drawable.Drawable

data class AppUsageStatsInfo(
    val appName: String? = null,
    val packageName: String? = null,
    val lastTimeUsed: Long? = null,
    val totalTimeInForeground: String? = null,
    val icon: Drawable? = null
)
