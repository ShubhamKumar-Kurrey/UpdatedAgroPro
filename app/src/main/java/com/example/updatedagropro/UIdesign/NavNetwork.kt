package com.example.updatedagropro.UIdesign

sealed interface Constraints
sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object ReadingsScreen : Screen("home")
    object DetailsScreen : Screen("details")
    object Sun_Wind : Screen("sun")

}
