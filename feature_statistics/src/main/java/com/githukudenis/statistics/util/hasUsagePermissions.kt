package com.githukudenis.statistics.util

import android.app.AppOpsManager
import android.content.Context
import android.os.Process

fun Context.hasUsagePermissions(): Boolean {
    val appOpsManager = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
    return appOpsManager.checkOpNoThrow(
        "android:get_usage_stats",
        Process.myUid(),
        packageName
    ) == AppOpsManager.MODE_ALLOWED
}