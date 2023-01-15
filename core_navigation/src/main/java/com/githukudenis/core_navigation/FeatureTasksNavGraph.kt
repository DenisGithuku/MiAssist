package com.githukudenis.core_navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.githukudenis.tasks.ui.add_task.AddTaskScreen
import com.githukudenis.tasks.ui.task_detail.TaskDetailScreen
import com.githukudenis.tasks.ui.task_list.TaskListScreen

const val FeatureTasksNavGraphRouteId = "tasks_graph"

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.featureTasksNavGraph(
    snackbarHostState: SnackbarHostState,
    navHostController: NavHostController
) {
    navigation(
        startDestination = MiAssistScreenDestination.TaskListDestination.routeId,
        route = FeatureTasksNavGraphRouteId
    ) {
        composable(route = MiAssistScreenDestination.TaskListDestination.routeId) {
            TaskListScreen(
                snackbarHostState = snackbarHostState,
                onNewTask = {
                    navHostController.navigate(route = MiAssistScreenDestination.AddTaskDestination.routeId) {
                        launchSingleTop = true
                        popUpTo(MiAssistScreenDestination.AddTaskDestination.routeId) {
                            inclusive = true
                        }
                    }
                },
                onOpenTodoDetails = { taskId ->
                    navHostController.navigate(MiAssistScreenDestination.TaskDetailDestination.routeId + "/$taskId") {
                        launchSingleTop = true
                        popUpTo(MiAssistScreenDestination.TaskDetailDestination.routeId) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = MiAssistScreenDestination.TaskDetailDestination.routeId + "/{taskId}") {
            TaskDetailScreen(
                snackbarHostState = snackbarHostState,
                onUpdateTask = {
                    navHostController.popBackStack()
                },
                onNavigateUp = { navHostController.navigateUp() }
            )
        }
        composable(route = MiAssistScreenDestination.AddTaskDestination.routeId) {
            AddTaskScreen(
                snackbarHostState = snackbarHostState,
                onSaveTask = {
                    navHostController.popBackStack()
                },
                onNavigateUp = { navHostController.navigateUp() }
            )
        }
    }
}