package com.githukudenis.core_navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MiAssistNavigator(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    NavHost(navController = navController, startDestination = FeatureTasksNavGraphRouteId) {
        featureTasksNavGraph(
            snackbarHostState = snackbarHostState,
            navHostController = navController
        )
        featureStatsNavGraph(
            snackbarHostState = snackbarHostState,
            navHostController = navController
        )
    }
}