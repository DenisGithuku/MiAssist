package com.githukudenis.miassist

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.*
import androidx.navigation.compose.rememberNavController
import com.githukudenis.core_navigation.MiAssistNavigator
import com.githukudenis.miassist.ui.theme.TodoeyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            TodoeyTheme {
                val navHostController = rememberNavController()


                MiAssistNavigator(
                    navController = navHostController,
                    snackbarHostState =
                )
            }
        }
    }
}
