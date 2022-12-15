package com.githukudenis.todoey.data.local

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime

private const val TAG = "converters"
class Converters {
    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun stringToPriority(value: String): Priority {
        return Priority.valueOf(value)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun stringToDate(value: String): LocalDate? {
        return try {
            LocalDate.parse(value)
        } catch (e: Exception) {
            Log.e(TAG, "fromDate: ${e.localizedMessage}")
            null
        }
    }

    @TypeConverter
    fun fromDate(localDate: LocalDate): String? {
        return try {
            localDate.toString()
        } catch (e: Exception) {
            Log.e(TAG, "fromDate: ${e.localizedMessage}")
            null
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun stringToTime(value: String): LocalTime? {
        return try {
            LocalTime.parse(value)
        } catch (e: Exception) {
            Log.e(TAG, "fromDate: ${e.localizedMessage}")
            null
        }
    }

    @TypeConverter
    fun fromLocalTime(localTime: LocalTime): String? {
        return try {
            localTime.toString()
        } catch (e: Exception) {
            Log.e(TAG, "fromDate: ${e.localizedMessage}")
            null
        }
    }
}