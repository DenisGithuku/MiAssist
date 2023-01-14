package com.githukudenis.tasks

import androidx.annotation.DrawableRes

sealed class MiAssistScreen(val routeId: String, @DrawableRes val iconId: Int?) {
    object TaskList : MiAssistScreen(routeId = "Tasks", iconId = R.drawable.task_list)
    object TaskDetail : MiAssistScreen(routeId = "Details", iconId = null)
    object AddTask : MiAssistScreen(routeId = "New task", iconId = null)
}