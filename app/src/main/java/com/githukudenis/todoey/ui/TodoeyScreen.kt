package com.githukudenis.todoey.ui

import androidx.annotation.DrawableRes
import com.githukudenis.todoey.R

sealed class TodoeyScreen(val routeId: String, @DrawableRes val iconId: Int?) {
    object TaskList : TodoeyScreen(routeId = "Tasks", iconId = R.drawable.task_list)
    object TaskDetail : TodoeyScreen(routeId = "Details", iconId = null)
    object AddTask : TodoeyScreen(routeId = "New task", iconId = null)
}