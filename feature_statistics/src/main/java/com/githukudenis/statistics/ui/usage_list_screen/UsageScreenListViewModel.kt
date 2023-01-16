package com.githukudenis.statistics.ui.usage_list_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.core_data.util.UserMessage
import com.githukudenis.statistics.domain.repository.AppStatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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

    fun onEvent(event: UsageScreenListEvent) {
        when (event) {
            is UsageScreenListEvent.OnShowUserMessage -> {
                _uiState.update { state ->
                    val userMessages = state.userMessages.filterNot { userMessage ->
                        userMessage == event.userMessage
                    }
                    state.copy(
                        userMessages = userMessages
                    )
                }
            }
        }
    }

    private fun getUsageStats() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            appUsageStatsRepository.getUsageStats().catch { error ->
                _uiState.update { state ->
                    val userMessages = mutableListOf<UserMessage>()
                    val userMessage = UserMessage(message = error.message)
                    userMessages.add(userMessage)
                    state.copy(userMessages = userMessages, isLoading = false)
                }
            }.collect { usageStats ->
                _uiState.update {
                    it.copy(
                        data = usageStats,
                        isLoading = false
                    )
                }
            }
        }
    }
}