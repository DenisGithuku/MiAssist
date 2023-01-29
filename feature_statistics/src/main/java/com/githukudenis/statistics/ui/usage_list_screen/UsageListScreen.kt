package com.githukudenis.statistics.ui.usage_list_screen

import android.app.usage.UsageStats
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.githukudenis.statistics.util.hasUsagePermissions

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun UsageListScreen(
    snackbarHostState: SnackbarHostState,
    onNavigateUp: () -> Unit
) {
    val context = LocalContext.current
    var isDialogOpen by remember {
        mutableStateOf(false)
    }
    val dialogProperties =
        DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false)
    val permissionAllowed = remember {
        derivedStateOf { context.hasUsagePermissions() }
    }

    if (!permissionAllowed.value) {
        AlertDialog(properties = dialogProperties, title = {
            Text(
                text = "Usage permissions"
            )
        }, text = {
            Text(
                text = "MiAssist needs access to app usage permissions in order to show statistics information"
            )
        }, confirmButton = {
            Button(onClick = {
                Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS).apply {
                    context.startActivity(this)
                }
            }) {
                Text(
                    text = "Grant access",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }, dismissButton = {
            Button(onClick = {
                isDialogOpen = !isDialogOpen
                onNavigateUp()
            }) {
                Text(
                    text = "Cancel",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }, shape = MaterialTheme.shapes.large, onDismissRequest = {
            if (permissionAllowed.value) {
                isDialogOpen = !isDialogOpen
            } else {
                isDialogOpen = !isDialogOpen
                onNavigateUp()
            }
        })
    }

    val usageScreenListViewModel: UsageScreenListViewModel = hiltViewModel()
    val lifecycleOwner = LocalLifecycleOwner.current
    val screenState = usageScreenListViewModel.uiState.collectAsStateWithLifecycle().value

    if (screenState.userMessages.isNotEmpty()) {
        LaunchedEffect(screenState.userMessages, snackbarHostState) {
            val userMessage = screenState.userMessages[0]
            snackbarHostState.showSnackbar(
                message = userMessage.message ?: return@LaunchedEffect
            )
            usageScreenListViewModel.onEvent(UsageScreenListEvent.OnShowUserMessage(userMessage = userMessage))
        }
    }
    UsageListScreen(
        appUsageStatsInfoList = screenState.data
    )
}

@Composable
private fun UsageListScreen(
    appUsageStatsInfoList: List<UsageStats>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    LazyColumn {
        items(appUsageStatsInfoList) { usageStat ->
            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = usageStat.packageName
                )
                Text(
                    text = "${usageStat.totalTimeInForeground}"
                )
            }
        }
    }
}