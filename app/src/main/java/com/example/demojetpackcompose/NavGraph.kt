package com.example.demojetpackcompose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.demojetpackcompose.screens.DetailsScreen
import com.example.demojetpackcompose.screens.HomeScreen

@Composable
fun SetUpNavHost(modifier: Modifier = Modifier, navHostController: NavHostController) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = Screens.Home.route
    ) {
        composable(Screens.Home.route) {
            HomeScreen(navController = navHostController)
        }
        composable(Screens.Details.route) {
            DetailsScreen(navController = navHostController)
        }
    }
}