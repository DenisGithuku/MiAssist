package com.githukudenis.miassist

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.githukudenis.core_navigation.MiAssistNavigator
import com.githukudenis.core_navigation.MiAssistScreenDestination
import com.githukudenis.miassist.ui.theme.TodoeyTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
        }

        setContent {
            // Remember a SystemUiController
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = !isSystemInDarkTheme()

            DisposableEffect(systemUiController, useDarkIcons) {
                // Update all of the system bar colors to be transparent, and use
                // dark icons if we're in light theme
                systemUiController.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = useDarkIcons
                )

                // setStatusBarColor() and setNavigationBarColor() also exist

                onDispose {}
            }

            TodoeyTheme {
                val navHostController = rememberNavController()
                val snackbarHostState = remember {
                    SnackbarHostState()
                }
                val backStackState by navHostController.currentBackStackEntryAsState()
                val currentDestination = backStackState?.destination

                Scaffold(bottomBar = {
                    NavigationBar {
                        MiAssistScreenDestination.bottomBarScreens.forEach { screenDestination ->
                            NavigationBarItem(
                                selected = screenDestination.routeId == currentDestination?.route,
                                onClick = {
                                    if (screenDestination.routeId == currentDestination?.route) return@NavigationBarItem
                                    navHostController.navigate(
                                        screenDestination.routeId
                                    ) {
                                        backStackState?.destination?.parent?.startDestinationRoute?.let { startDestination ->
                                            popUpTo(startDestination)
                                        }
                                    }
                                },
                                label = {
                                    Text(
                                        text = screenDestination.label ?: return@NavigationBarItem,
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                },
                                icon = {
                                    screenDestination.iconId?.let { icon ->
                                        Icon(
                                            painterResource(id = icon),
                                            contentDescription = null
                                        )
                                    }
                                }
                            )
                        }
                    }
                }) { contentPadding ->
                    Column(modifier = Modifier.padding(contentPadding)) {
                        MiAssistNavigator(
                            navController = navHostController,
                            snackbarHostState = snackbarHostState
                        )
                    }
                }
            }
        }
    }
}
