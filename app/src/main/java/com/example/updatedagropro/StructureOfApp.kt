package com.example.updatedagropro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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


@Composable

fun MainScreen() {

    val navController= rememberNavController()
    Scaffold (

        topBar = {
            topBar(name = "AgroPro", modifier = Modifier
                .fillMaxWidth(1f)
                .background(color = Color.Blue)
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
                    name = "Average",
                    route = "details",
                    icon = Icons.Default.Settings
                )

            )
                , navController =navController
                , onItemClick = {
                    navController.navigate(it.route)
                })
        }

            ){
        Navigation(navController = navController)
        //SlaveNavBadges()
    }
}

@Composable
fun topBar(name: String,
           modifier: Modifier,


) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
    )
    {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )
        
        
            Text(
                text = name,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Green
            )
            
        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Notification",
            modifier = Modifier.size(24.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Notification",
            modifier = Modifier.size(24.dp)
        )
    }
}


@Composable
fun Navigation(navController: NavHostController){
    NavHost(navController = navController, startDestination = "home" ){
        composable(route = "home"){
            ReadingsScreen()
        }
        composable(route = "details"){
            DetailScreen()
        }
    }
}

@Composable
fun Navigation1(navController: NavHostController) {
    NavHost(navController = navController,startDestination = "Slave 1" ){
        composable(route="Slave 1"){
            Slave1()
        }
        composable(route="Slave 2"){
            Slave2(navHostController = navController)
        }
    }
}


@Composable
fun BottomNavBadges(
    items: List<NavitemS>,
    navController: NavController,
    onItemClick: (NavitemS) -> Unit
) {
    val backStackEntry=navController.currentBackStackEntryAsState()
    BottomNavigation(
        backgroundColor = Color.Blue,
        elevation = 5.dp
    ) {
        items.forEach{item ->
            val selected= item.route==backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = Color.Green,
                unselectedContentColor = Color.LightGray,
                icon={

                        Column(horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Detail",
            color = Color.White,
            fontSize = MaterialTheme.typography.h3.fontSize,
            fontWeight = FontWeight.Bold
        )

    }
}


