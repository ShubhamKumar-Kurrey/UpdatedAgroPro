package com.example.updatedagropro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.updatedagropro.WeatherForecast.WeatherPage
import kotlinx.coroutines.launch

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    onDestinationClicked: (route: String) -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(start = 24.dp, top = 48.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "App icon"
        )
        screens.forEach { screen ->
            Spacer(Modifier.height(24.dp))
            Text(
                text = screen.title,
                style = MaterialTheme.typography.h4,
                modifier = Modifier.clickable {
                    onDestinationClicked(screen.route)
                }
            )
        }
    }
}



@Composable
fun TopBar(title: String = "", buttonIcon: ImageVector, onButtonClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(onClick = { onButtonClicked() } ) {
                Icon(buttonIcon, contentDescription = "",
                    tint = Color.White)

            }
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
        },
        backgroundColor = Color(0xFF35207B)
    )
}

@Composable
fun Home(openDrawer: () -> Unit) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val openDrawer = {
        scope.launch {
            drawerState.open()
        }
    }
    Scaffold(

        topBar = {
            TopBar(
                title = "AgroPro 2.0",
                buttonIcon = Icons.Filled.Menu,
                onButtonClicked = { openDrawer() },

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
        ModalDrawer(modifier = Modifier
            .fillMaxSize()
            ,
            drawerState = drawerState,
            gesturesEnabled = drawerState.isOpen,
            drawerContent = {
                Drawer(
                    onDestinationClicked = { route ->
                        scope.launch {
                            drawerState.close()
                        }
                        navController.navigate(route) {
                            popUpTo = navController.graph.startDestinationId
                            launchSingleTop = true
                        }
                    }
                )
            }
        ){

            Navigation(navController = navController)
        }

    }
}

@Composable
fun WeatherForecast(openDrawer: () -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text(text = "Today's Weather", style = MaterialTheme.typography.h4)
            WeatherPage()
        }
}


@Composable
fun Help(navController: NavController) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Help.", style = MaterialTheme.typography.h4)
        }

}
