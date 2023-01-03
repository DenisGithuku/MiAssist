package com.githukudenis.statistics.domain.repository

import android.app.usage.UsageStats

interface AppStatsRepository {

    suspend fun getUsageStats(): List<UsageStats>
}