package com.githukudenis.todoey

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.githukudenis.tasks.TodoeyScreen
import com.githukudenis.tasks.add_task.AddTaskScreen
import com.githukudenis.tasks.task_detail.TaskDetailScreen
import com.githukudenis.tasks.task_list.TaskListScreen

@Composable
fun TodoeyNavigator(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = com.githukudenis.tasks.TodoeyScreen.TaskList.routeId) {
        composable(route = com.githukudenis.tasks.TodoeyScreen.TaskList.routeId) {
            com.githukudenis.tasks.task_list.TaskListScreen(
                onNewTask = {
                    navController.navigate(route = com.githukudenis.tasks.TodoeyScreen.AddTask.routeId) {
                        launchSingleTop = true
                        popUpTo(com.githukudenis.tasks.TodoeyScreen.AddTask.routeId) {
                            inclusive = true
                        }
                    }
                },
                onOpenTodoDetails = { taskId ->
                    navController.navigate(com.githukudenis.tasks.TodoeyScreen.TaskDetail.routeId + "/$taskId") {
                        launchSingleTop = true
                        popUpTo(com.githukudenis.tasks.TodoeyScreen.TaskDetail.routeId) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = com.githukudenis.tasks.TodoeyScreen.TaskDetail.routeId + "/{taskId}") {
            com.githukudenis.tasks.task_detail.TaskDetailScreen(onUpdateTask = {
                navController.popBackStack()
            }, onNavigateUp = { navController.navigateUp() })
        }
        composable(route = com.githukudenis.tasks.TodoeyScreen.AddTask.routeId) {
            com.githukudenis.tasks.add_task.AddTaskScreen(
                onSaveTask = {
                    navController.popBackStack()
                },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}