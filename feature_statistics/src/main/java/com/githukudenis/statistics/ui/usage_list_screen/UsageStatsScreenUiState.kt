package com.githukudenis.statistics.ui.usage_list_screen

import android.app.usage.UsageStats
import com.githukudenis.core_data.util.UserMessage

data class UsageStatsScreenUiState(
    val isLoading: Boolean = false,
    val data: List<UsageStats> = emptyList(),
    val userMessages: List<UserMessage> = emptyList()
)