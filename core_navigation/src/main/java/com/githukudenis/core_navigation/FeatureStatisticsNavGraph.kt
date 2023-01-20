package com.githukudenis.core_navigation

import androidx.compose.material3.SnackbarHostState
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
        startDestination = MiAssistScreenDestination.UsageListScreenDestination.routeId,
        route = FeatureStatisticsGraphRouteId
    ) {
        composable(
            route = MiAssistScreenDestination.UsageListScreenDestination.routeId
        ) {
            UsageListScreen(
                snackbarHostState = snackbarHostState,
                onNavigateUp = {
                    navHostController.navigateUp()
                }
            )
        }

        composable(
            route = MiAssistScreenDestination.AppUsageDetailScreenDestination.routeId
        ) {
        }
    }
}