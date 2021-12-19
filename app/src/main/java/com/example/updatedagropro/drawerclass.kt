package com.example.updatedagropro

sealed class DrawerScreens(val title: String, val route: String) {
    object Home : DrawerScreens("Home", "home")
    object WeatherForecast : DrawerScreens("Weather Forecast", "weather")
    object pHsensor : DrawerScreens("pH Sensor", "pHsensor")
    object Help : DrawerScreens( "Help", "help")
}

val screens = listOf(
    DrawerScreens.Home,
    DrawerScreens.WeatherForecast,
    DrawerScreens.pHsensor,
    DrawerScreens.Help
)