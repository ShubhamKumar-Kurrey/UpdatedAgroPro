package com.example.updatedagropro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Notification
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.updatedagropro.SunWind.Sun_Wind
import com.example.updatedagropro.network.API
import com.example.updatedagropro.network.SensorData
import com.example.updatedagropro.pHscale.pHsensor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.util.*
import kotlin.concurrent.fixedRateTimer
import kotlin.concurrent.timer
import kotlin.concurrent.timerTask


@Composable
fun AppMainScreen() {
    val navController1 = rememberNavController()
        Surface(color = Color(0xFFA188F5)) {
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val openDrawer = {
                scope.launch {
                    drawerState.open()
                }
            }
            ModalDrawer(
                drawerState = drawerState,
                gesturesEnabled = drawerState.isOpen,
                drawerContent = {
                    Drawer(
                        onDestinationClicked = { route ->
                            scope.launch {
                                drawerState.close()
                            }
                            navController1.navigate(route) {
                                popUpTo = navController1.graph.startDestinationId
                                launchSingleTop = true
                            }
                        }
                    )
                }
            ) {
                NavHost(
                    navController = navController1, startDestination = DrawerScreens.Home.route
                ) {
                    composable(DrawerScreens.Home.route) {
                        Home(
                            openDrawer = {
                                openDrawer()
                            }
                        )
                    }
                }
            }
        }
}



@Composable
fun Navigation(navController: NavHostController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val openDrawer = {
        scope.launch {
            drawerState.open()
        }
    }

    NavHost(navController = navController, startDestination = "home") {
        composable(route = "home") {
            ReadingsScreen()
        }
        composable(route = "details") {
            DetailScreen()
        }
        composable(route = "sun") {
            Sun_Wind()
        }
        composable(DrawerScreens.WeatherForecast.route) {
            WeatherForecast(
                openDrawer = {
                    openDrawer()
                }
            )
        }
        composable(DrawerScreens.pHsensor.route) {
            pHsensor(
                openDrawer = {
                    openDrawer()
                }
            )
        }
        composable(DrawerScreens.Help.route) {
            Help(
                navController = navController
            )
        }
    }
}


@Composable
fun BottomNavBadges(
    items: List<NavitemS>,
    navController: NavController,
    onItemClick: (NavitemS) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier=Modifier.clip(RoundedCornerShape(50.dp)),
        backgroundColor = Color(0xFFFFFFFF),
        elevation = 5.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = Color(0xFF1AB68A),
                unselectedContentColor = Color(0xFFCCCCCC),
                icon = {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(item.icon, contentDescription = null)
                        if (selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )
                        }
                    }

                }

            )
        }
    }

}


@Composable
fun DetailScreen() {
    var data by remember {
        mutableStateOf<Result<SensorData>>(Result.failure(Exception("Initial")))
    }

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit) {
        fixedRateTimer("observer", startAt = Date(), period = 1000) {
            scope.launch(Dispatchers.IO) { withTimeout(2000) { data = API.getSensorData("1") }}
        }
    }

    if (data.isSuccess) {
        val sensor = data.getOrNull()!!
        Column {
            Text("Humidity = " + sensor.ah)
            Text("Soil moisture = " + sensor.sm)
            Text("Soil Temp = " + sensor.st)
            Text("Atm Temp = " + sensor.at)
        }
    } else {
        Text("No data: " + data.exceptionOrNull())
    }
}





