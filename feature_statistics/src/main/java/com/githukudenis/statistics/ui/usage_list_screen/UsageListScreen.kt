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
                onNavigateUp()
            }) {
                Text(
                    text = "Cancel",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }, shape = MaterialTheme.shapes.large, onDismissRequest = {
            if (!permissionAllowed.value) {
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
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 16.dp, horizontal = 8.dp)
    ) {
        item {
            // get total time in foreground list values
            val sumOfAllValues =
                appUsageStatsInfoList.sumOf { it.totalTimeInForeground.toInt() }.toFloat()

            // splice the first four values (most used apps)
            val firstFiveValues =
                appUsageStatsInfoList.take(4).map { it.totalTimeInForeground.toFloat() }
                    .toMutableList()

            // get  the sum of the other values after first four
            val sumOfValuesAfterFirstFive =
                appUsageStatsInfoList.drop(4).sumOf { it.totalTimeInForeground.toInt() }.toFloat()

            // list of first five and sum of others
            firstFiveValues.add(sumOfValuesAfterFirstFive)

            val colors = listOf(
                Color.Red.copy(green = .7f),
                Color.Green.copy(red = .7f),
                Color.LightGray,
                Color.Black.copy(alpha = .7f),
                Color.Yellow.copy(green = .7f)
            )

            // generate pairs for each value and the corresponding colors
            val statsWithColors = firstFiveValues zip colors

            // map out values
            val plotValues = statsWithColors.map { statInfo ->
                statInfo.first * 100 / sumOfAllValues
            }

            val angles = plotValues.map { angleValue ->
                angleValue * 360f / 100
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Canvas(modifier = modifier.size(150.dp)) {
                    var startAngle = -90f

                    for (i in angles.indices) {
                        drawArc(
                            color = statsWithColors.map { it.second }[i],
                            startAngle = startAngle,
                            sweepAngle = angles[i],
                            useCenter = false,
                            style = Stroke(
                                width = 16.dp.value,
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round
                            )
                        )
                        // increase angle after drawing arc
                        startAngle += angles[i]
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
                        text = applicationInfoMapper.getTimeFromMillis(usageStat.totalTimeInForeground)
                    )
                }
            }
        }
    }
}