package com.githukudenis.statistics.ui.usage_list_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.statistics.domain.repository.AppStatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsageScreenListViewModel @Inject constructor(
    private val appUsageStatsRepository: AppStatsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UsageStatsScreenUiState())
    val uiState: StateFlow<UsageStatsScreenUiState> get() = _uiState.asStateFlow()

    init {
        getUsageStats()
    }

    private fun getUsageStats() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            val usageStats = appUsageStatsRepository.getUsageStats()
            _uiState.update {
                it.copy(
                    data = usageStats
                )
            }
        }
    }
}