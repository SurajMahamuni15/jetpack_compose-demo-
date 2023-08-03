package com.example.demojetpackcompose

sealed class Screens(val route: String) {
    object Home : Screens("home_screen")
    object Details : Screens("details_screen")
}
