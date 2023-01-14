package com.githukudenis.statistics.ui

sealed class StatisticScreenDestination(val routeId: String, val icon: Int? = null) {
    object UsageListScreenDestination: StatisticScreenDestination(routeId = "usage_list")
    object AppUsageDetailScreenDestination: StatisticScreenDestination(routeId = "app_usage_details")
}