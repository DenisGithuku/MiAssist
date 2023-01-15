package com.githukudenis.statistics.ui.usage_screen_list

import android.app.usage.UsageStats

data class UsageStatsScreenUiState(
    val isLoading: Boolean = false,
    val data: List<UsageStats> = emptyList(),
//    val userMessages: List<UserMessage> = emptyList()
)