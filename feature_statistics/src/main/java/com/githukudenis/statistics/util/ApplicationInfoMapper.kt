package com.githukudenis.statistics.util

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable

class ApplicationInfoMapper(private val context: Context) {

    fun getTimeFromMillis(timeInMillis: Long): String {
        return if (timeInMillis / 1000 / 60 / 60 >= 1) {
            "${timeInMillis / 1000 / 60 / 60} hr ${timeInMillis / 1000 / 60 % 60} min"
        } else if (timeInMillis / 1000 / 60 >= 1) {
            "${timeInMillis / 1000 / 60} min"
        } else if (timeInMillis / 1000 >= 1) {
            "Less than a minute"
        } else {
            "0 min"
        }
    }

    fun getIconFromPackage(applicationInfo: ApplicationInfo): Drawable {
        return context.packageManager.getApplicationIcon(applicationInfo)
    }

    fun getApplicationName(applicationInfo: ApplicationInfo): String {
        return context.packageManager.getApplicationLabel(
            applicationInfo
        ).toString()
    }

    fun getApplicationName(packageName: String): String {
        val applicationInfo =
            context.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
                .find {
                    it.packageName == packageName
                }
        return context.packageManager.getApplicationLabel(applicationInfo!!).toString()
    }
}