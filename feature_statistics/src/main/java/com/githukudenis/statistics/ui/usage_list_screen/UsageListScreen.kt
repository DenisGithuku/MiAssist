package com.githukudenis.statistics.ui.usage_list_screen

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.githukudenis.statistics.domain.model.AppUsageStatsInfo
import com.githukudenis.statistics.util.ApplicationInfoMapper
import com.githukudenis.statistics.util.hasUsagePermissions
import com.google.accompanist.drawablepainter.rememberDrawablePainter

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

    if (screenState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Loading apps..."
                )
            }
        }
    }

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
    appUsageStatsInfoList: List<AppUsageStatsInfo>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val applicationInfoMapper = ApplicationInfoMapper(context)
    LazyColumn(
        modifier = modifier.padding(8.dp)
    ) {
        item {
            val sumOfValues = appUsageStatsInfoList.sumOf { it.totalTimeInForeground?.toInt() ?: 0 }.toFloat()

            val proportions = appUsageStatsInfoList.map {
                it.totalTimeInForeground?.toFloat()
            }.map {
                it?.let { it * 100 / sumOfValues }
            }

            val sweepAngles = proportions.map {
                it?.let {
                    it * 360 / 100
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Canvas(modifier = modifier.size(100.dp)) {
                    var startAngle = -90f

                    for (i in sweepAngles.indices) {
                        drawArc(
                            color = Color.Blue,
                            startAngle = startAngle,
                            sweepAngle = sweepAngles[i]!!,
                            useCenter = false,
                            style = Stroke(
                                width = 8.dp.value,
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round
                            )
                        )
                        startAngle += sweepAngles[i]!!
                    }
                }
            }
        }
        items(appUsageStatsInfoList) { usageStat ->
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    painter = rememberDrawablePainter(drawable = usageStat.icon),
                    contentDescription = "App icon"
                )
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                ) {
                    Text(
                        text = usageStat.appName ?: return@Column
                    )
                    Spacer(modifier = modifier.height(12.dp))
                    Text(
                        text = applicationInfoMapper.getTimeFromMillis(usageStat.totalTimeInForeground) ?: return@Column
                    )
                }
            }
        }
    }
}