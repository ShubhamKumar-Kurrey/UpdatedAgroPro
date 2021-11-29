package com.example.updatedagropro

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun ReadingsScreen(){
    Box(modifier = Modifier
        .fillMaxSize(1f)
        .padding(10.dp),
    contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxWidth(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround) {
               /* LazyRow(Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween){
                    items(1){
                        Text(text ="Slave 1" ,fontSize = 20.sp,fontWeight = FontWeight.Bold,color = Color.Green)
                        Text(text ="Slave 2" ,fontSize = 20.sp,fontWeight = FontWeight.Bold)
                        Text(text ="Slave 3" ,fontSize = 20.sp,fontWeight = FontWeight.Bold)
                        Text(text ="Slave 4" ,fontSize = 20.sp,fontWeight = FontWeight.Bold)
                    }
                    }*/

               Row(modifier = Modifier
                   .padding(40.dp)
                   .fillMaxWidth(1f),
                   horizontalArrangement = Arrangement.SpaceBetween,
               verticalAlignment = Alignment.Top) {
                   Sensor(value = 0.2f, text = "Soil Temperature")
                   Sensor(value = 0.86f, text = "Soil Moisture")
               }
                Row(modifier = Modifier
                    .padding(40.dp)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Sensor(value = 0.4f, text = "Air Temperature")
                    Sensor(value = 0.56f, text = "Moisture")
                }
            }
           
       }

    }
}

@Composable
fun Sensor(value: Float,
                text: String) {
    Box() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            circularProgressBar(percentage = value, number = 100)
            Text(text =text ,fontSize = 15.sp,fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun circularProgressBar(
    radius: Dp = 50.dp,
    strokeWidth: Dp =8.dp,
    percentage :Float,
    color: Color =if (percentage>0.8f) {
        Color.Red
    }
    else {
        Color.Blue
    },
    number: Int,
    fontSize: TextUnit =28.sp,
    animeDuration: Int=1000,
    animDelay: Int=0
){


    var animationPlayed by remember{
        mutableStateOf(false)
    }
    val curPercentage= animateFloatAsState(
        targetValue = if (animationPlayed)percentage else 0f,
        animationSpec = tween(
            durationMillis = animeDuration,
            delayMillis = animDelay
        )
    )
    LaunchedEffect(key1 = true){
        animationPlayed=true
    }
    Box (
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius*2f)
    ){
        Canvas(modifier = Modifier.size(radius*2f)) {
            drawArc(
                color = color,
                -90f,
                360 * curPercentage.value,
                useCenter = false,
                style= Stroke(strokeWidth.toPx(), cap= StrokeCap.Round)
            )
        }
        val valuenumber=curPercentage.value*number

        Text(
            text = valuenumber.toInt().toString(),
            color = Color.Black,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )

    }
}


@Composable
fun Slaves(name: String,modifier: Modifier) {
    Text(text=name,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(10.dp),
        color = Color.Red
    )
}


@Composable
fun Slave1() {
    Sensor(value = 0.3f, text = "Mositure")
}

@Composable
fun Slave2(navHostController: NavHostController) {
    DetailScreen()
}

@Composable
fun SlaveNavBadges(
    items: List<SlaveItem>,
    navController: NavController,
    onItemClick: (SlaveItem) -> Unit
)
 {
     val backStackEntry=navController.currentBackStackEntryAsState()
     items.forEach{item ->
         val selected= item.route==backStackEntry.value?.destination?.route
             LazyRow() {
               items(1) {

                     /*  Column(
                           horizontalAlignment = Alignment.CenterHorizontally,
                           verticalArrangement = Arrangement.Center
                       ) {*/
                           Text(
                               text = item.name,
                               textAlign = TextAlign.Center,
                               fontSize = 25.sp,
                               color = Color.Red
                           )
                          /* if (selected) {
                               Text(
                                   text = item.name,
                                   textAlign = TextAlign.Center,
                                   fontSize = 30.sp,
                                   color = Color.Green
                               )
                           }*/
                       //}
                   }
               }





     }
    
}

@Composable
fun Slaves() {
    val navController= rememberNavController()
    SlaveNavBadges(items = listOf(
        SlaveItem(
     name = "Slave 1",
     route = "Slave 1"
    ),
        SlaveItem(
            name = "Slave 2",
            route = "Slave 2"
        ),
        SlaveItem(
            name = "Slave 3",
            route = "Slave 3"
        ),
        SlaveItem(
            name = "Slave 4",
            route = "Slave 4"
        ),


    ), navController =navController , onItemClick ={
        navController.navigate(it.route)
    })
}