package com.githukudenis.statistics.ui.usage_list_screen

import com.githukudenis.core_data.util.UserMessage

sealed interface UsageScreenListEvent {
    class OnShowUserMessage(val userMessage: UserMessage) : UsageScreenListEvent
}