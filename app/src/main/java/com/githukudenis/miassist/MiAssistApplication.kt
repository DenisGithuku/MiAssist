package com.githukudenis.miassist

import android.app.Application
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import dagger.hilt.android.HiltAndroidApp

const val notificationChannelId: String = "miassist_notifications"
const val notificationChannelName: String = "Tasks Reminders"

@HiltAndroidApp
class MiAssistApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationChannelCompat.Builder(
            notificationChannelId, NotificationCompat.PRIORITY_DEFAULT
        ).setShowBadge(true).setVibrationEnabled(true).build()
    }
}