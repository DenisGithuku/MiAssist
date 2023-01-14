package com.githukudenis.core_navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.githukudenis.statistics.ui.featureStatsNavGraph
import com.githukudenis.statistics.ui.usage_list_screen.UsageListScreen
import com.githukudenis.tasks.MiAssistScreen
import com.githukudenis.tasks.ui.add_task.AddTaskScreen
import com.githukudenis.tasks.ui.featureTasksNavGraph
import com.githukudenis.tasks.ui.task_detail.TaskDetailScreen
import com.githukudenis.tasks.ui.task_list.TaskListScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MiAssistNavigator(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    NavHost(navController = navController, startDestination = MiAssistScreen.TaskList.routeId) {
        featureTasksNavGraph(
            snackbarHostState = snackbarHostState,
            navHostController = navController
        )
        featureStatsNavGraph(
            snackbarHostState = snackbarHostState,
            navHostController = navController
        )
    }
}