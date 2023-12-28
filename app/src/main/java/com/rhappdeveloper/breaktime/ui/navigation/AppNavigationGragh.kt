package com.rhappdeveloper.breaktime.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rhappdeveloper.breaktime.ui.screens.HomeScreen

@Composable
fun AppNavigationGraph(innerPadding: PaddingValues) {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.HOME_SCREEN) {
        composable(Routes.HOME_SCREEN) {
            HomeScreen(innerPadding)
        }
    }

}