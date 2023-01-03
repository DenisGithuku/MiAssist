package com.githukudenis.tasks

import androidx.annotation.DrawableRes

sealed class TodoeyScreen(val routeId: String, @DrawableRes val iconId: Int?) {
    object TaskList : TodoeyScreen(routeId = "Tasks", iconId = R.drawable.task_list)
    object TaskDetail : TodoeyScreen(routeId = "Details", iconId = null)
    object AddTask : TodoeyScreen(routeId = "New task", iconId = null)
}