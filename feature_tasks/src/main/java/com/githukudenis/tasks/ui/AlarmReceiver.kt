package com.githukudenis.tasks.ui

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.githukudenis.tasks.R
import com.githukudenis.tasks.util.Constants

private const val notificationId: Int = 1001
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            notify(
                message = it.extras?.getString("Message") ?: return,
                context = context!!,
                notificationId = notificationId,
                channelId = Constants.notificationChannelId
            )
        }
    }

    private fun notify(message: String, context: Context, notificationId: Int, channelId: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Task Reminder")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.clock)
            .build()

        notificationManager.notify(
            notificationId,
            notification
        )
    }
}