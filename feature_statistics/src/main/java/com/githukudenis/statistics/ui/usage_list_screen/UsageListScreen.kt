package com.githukudenis.statistics.ui.usage_list_screen

import android.app.usage.UsageStats
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun UsageListScreen(
    snackbarHostState: SnackbarHostState
) {
    val usageScreenListViewModel: UsageScreenListViewModel = hiltViewModel()
    val lifecycleOwner = LocalLifecycleOwner.current
    val viewModelState = remember(usageScreenListViewModel.uiState, lifecycleOwner) {
        usageScreenListViewModel.uiState.flowWithLifecycle(
            lifecycle = lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }

    val screenState = viewModelState.collectAsStateWithLifecycle(initialValue = UsageStatsScreenUiState()).value

    LaunchedEffect(screenState.userMessages, snackbarHostState) {
        val userMessage = screenState.userMessages[0]
        snackbarHostState.showSnackbar(
            message = userMessage.message ?: return@LaunchedEffect
        )
        usageScreenListViewModel.onEvent(UsageScreenListEvent.OnShowUserMessage(userMessage = userMessage))
    }
    UsageListScreen(
        usageStats = screenState.data
    )
}

@Composable
private fun UsageListScreen(
    usageStats: List<UsageStats>,
    modifier: Modifier = Modifier
) {
    LazyColumn {
        items(usageStats) { usageStat ->
            Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = usageStat.packageName
                )
                Text(
                    text = "${usageStat.lastTimeUsed}"
                )
            }
        }
    }
}