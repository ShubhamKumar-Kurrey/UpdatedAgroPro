package com.example.updatedagropro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Notification
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.updatedagropro.network.API
import com.example.updatedagropro.network.SensorData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.util.*
import kotlin.concurrent.fixedRateTimer
import kotlin.concurrent.timer
import kotlin.concurrent.timerTask


@Composable

fun MainScreen() {

    val navController = rememberNavController()
    Scaffold(

        topBar = {
            TopAppBar(backgroundColor = Color.Blue,
                title = {
                    Text(
                        "AgroPro 2.0",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = Color.Green
                    )
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.Filled.AccountBox, contentDescription = "About Us",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.Filled.ExitToApp, contentDescription = "Power Off",
                            tint = Color.White
                        )
                    }
                }
            )

        },

        bottomBar = {
            BottomNavBadges(items = listOf(
                NavitemS(
                    name = "Readings",
                    route = "home",
                    icon = Icons.Default.Home
                ),
                NavitemS(
                    name = "Sun & Wind",
                    route = "sun",
                    icon = Icons.Default.Star
                ),
                NavitemS(
                    name = "Average",
                    route = "details",
                    icon = Icons.Default.Settings
                )

            ), navController = navController, onItemClick = {
                navController.navigate(it.route)
            })
        }

    ) {
        Navigation(navController = navController)
        //SlaveNavBadges()
    }
}


@Composable
fun Navigation(navController: NavHostController) {
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
        backgroundColor = Color.Blue,
        elevation = 5.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = Color.Green,
                unselectedContentColor = Color.White,
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
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(color = Color.White),
//        contentAlignment = Alignment.Center
//    ) {
//       /* Text(
//            text = "Detail",
//            color = Color.Red,
//            fontSize = MaterialTheme.typography.h3.fontSize,
//            fontWeight = FontWeight.Bold
//        )*/
//        LazyColumn(){
//            item {
//
//            }
//        }
//
//    }

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

@Composable
fun Sun_Wind() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column {
            circularProgressBar(percentage = 10f, number = 1)
            circularProgressBar(percentage = 30f, number = 1)
        }

    }
}


