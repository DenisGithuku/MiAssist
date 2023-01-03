package com.githukudenis.todoey

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.githukudenis.tasks.TodoeyScreen
import com.githukudenis.tasks.ui.add_task.AddTaskScreen
import com.githukudenis.tasks.ui.task_detail.TaskDetailScreen
import com.githukudenis.tasks.ui.task_list.TaskListScreen

@Composable
fun TodoeyNavigator(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = com.githukudenis.tasks.TodoeyScreen.TaskList.routeId) {
        composable(route = com.githukudenis.tasks.TodoeyScreen.TaskList.routeId) {
            TaskListScreen(
                onNewTask = {
                    navController.navigate(route = TodoeyScreen.AddTask.routeId) {
                        launchSingleTop = true
                        popUpTo(TodoeyScreen.AddTask.routeId) {
                            inclusive = true
                        }
                    }
                },
                onOpenTodoDetails = { taskId ->
                    navController.navigate(TodoeyScreen.TaskDetail.routeId + "/$taskId") {
                        launchSingleTop = true
                        popUpTo(TodoeyScreen.TaskDetail.routeId) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = TodoeyScreen.TaskDetail.routeId + "/{taskId}") {
            TaskDetailScreen(onUpdateTask = {
                navController.popBackStack()
            }, onNavigateUp = { navController.navigateUp() })
        }
        composable(route = TodoeyScreen.AddTask.routeId) {
            AddTaskScreen(
                onSaveTask = {
                    navController.popBackStack()
                },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}