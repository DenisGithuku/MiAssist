package com.githukudenis.statistics.ui.usage_screen_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.statistics.domain.repository.AppStatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsageScreenListViewModel @Inject constructor(
    private val appUsageStatsRepository: AppStatsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UsageStatsScreenUiState())

    init {
        getUsageStats()
    }

    private fun getUsageStats() {
        viewModelScope.launch {
            val usageStats = appUsageStatsRepository.getUsageStats()
        }
    }
}