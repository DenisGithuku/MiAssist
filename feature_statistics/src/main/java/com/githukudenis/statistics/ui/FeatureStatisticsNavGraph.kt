package com.githukudenis.statistics.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.githukudenis.statistics.ui.usage_list_screen.UsageListScreen

const val FeatureStatisticsGraphRouteId = "feature_usage"

fun NavGraphBuilder.featureStatsNavGraph(
    snackbarHostState: SnackbarHostState,
    navHostController: NavHostController
) {
    navigation(
        startDestination = StatisticScreenDestination.UsageListScreenDestination.routeId,
        route = FeatureStatisticsGraphRouteId
    ) {
        composable(
            route = StatisticScreenDestination.UsageListScreenDestination.routeId
        ) {
            UsageListScreen()
        }
    }
}