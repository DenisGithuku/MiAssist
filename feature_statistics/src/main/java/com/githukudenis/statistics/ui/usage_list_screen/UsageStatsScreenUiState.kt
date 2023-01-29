package com.githukudenis.statistics.ui.usage_list_screen

import com.githukudenis.core_data.util.UserMessage
import com.githukudenis.statistics.domain.model.AppUsageStatsInfo

data class UsageStatsScreenUiState(
    val isLoading: Boolean = false,
    val data: List<AppUsageStatsInfo> = emptyList(),
    val userMessages: List<UserMessage> = emptyList()
)