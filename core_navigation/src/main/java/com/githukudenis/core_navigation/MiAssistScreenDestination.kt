package com.githukudenis.core_navigation

import com.githukudenis.tasks.R

sealed class MiAssistScreenDestination(
    val routeId: String,
    val label: String? = null,
    val iconId: Int? = null
) {
    object TaskListDestination : MiAssistScreenDestination(
        routeId = "task_list",
        label = "Tasks",
        iconId = R.drawable.task_list
    )

    object TaskDetailDestination : MiAssistScreenDestination(routeId = "task_detail")
    object AddTaskDestination : MiAssistScreenDestination(routeId = "add_task")
    object UsageListScreenDestination : MiAssistScreenDestination(
        routeId = "usage_list",
        label = "App Usage",
        iconId = com.githukudenis.statistics.R.drawable.ic_dashboard
    )

    object AppUsageDetailScreenDestination :
        MiAssistScreenDestination(routeId = "app_usage_details")

    companion object {
        val bottomBarScreens = listOf(
            TaskListDestination,
            UsageListScreenDestination
        )
    }
}