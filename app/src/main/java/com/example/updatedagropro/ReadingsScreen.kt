package com.example.updatedagropro

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.updatedagropro.network.API
import com.google.accompanist.pager.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.nio.file.Files.setLastModifiedTime
//import okio.Utf8.size
import java.nio.file.Files.size
import java.util.*
import kotlin.concurrent.fixedRateTimer
import kotlin.io.path.ExperimentalPathApi


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ReadingsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(1f)
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        SwipingScreen()
    }
}

@Composable
fun Sensor(Value: Float, headingname: String) {
    Box() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            circularProgressBar(percentage = Value, number = 1, heading=headingname)
            Spacer(modifier = Modifier.padding(5.dp))
           // Text(fontSize = 17.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun Slaves(slave: Slave_Name, slaveId: String) {
    var at by remember {
        mutableStateOf(0f)
    }
    var sm by remember {
        mutableStateOf(0f)
    }
    var st by remember {
        mutableStateOf(0f)
    }
    var am by remember {
        mutableStateOf(0f)
    }

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit) {
        fixedRateTimer("observer", startAt = Date(), period = 1000) {
            scope.launch(Dispatchers.IO) {
                withTimeout(2000) {
                    val res = API.getSensorData(slaveId)
                    val reading = res.getOrNull()
                    if(reading != null){
                        am = reading.ah
                        at = reading.at
                        sm = reading.sm
                        st = reading.st

                    }
                }
            }

        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Text(text = slave.name, fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.Blue)
        for (i in 1..4) {
            val i: Int
            i = 0
        }
        Row(
            modifier = Modifier
                .padding(40.dp)
                .fillMaxWidth(1f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Sensor(Value = st,headingname="Soil Temperature")
            Spacer(modifier = Modifier.padding(horizontal = 25.dp))
            Sensor(Value = sm, headingname = "Soil Moisture")
        }
        Row(
            modifier = Modifier
                .padding(40.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Sensor(Value = at, headingname = "Air Temperature")
            Spacer(modifier = Modifier.padding(horizontal = 25.dp))
            Sensor(Value = am, headingname="Humidity")
        }
    }

}


@Composable
fun circularProgressBar(
    radius: Dp = 50.dp,
    strokeWidth: Dp = 12.dp,
    percentage: Float,
    color: Color = if (percentage >= 20f && percentage <= 45f) {
        Color.Blue
    } else if (percentage < 20f) {
        Color.Red
    } else if (percentage > 70f) {
        Color.Red
    } else if (percentage <= 70f && percentage >= 60) {
        Color.Blue
    } else {
        Color.Green
    },
    heading:String,
    number: Int,
    fontSize: TextUnit = 28.sp,
    animeDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val curPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animeDuration,
            delayMillis = animDelay
        )
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius * 2f)
    ) {
        Canvas(modifier = Modifier.size(radius * 2f)) {
            drawArc(
                color = color,
                 150f,
                 2.4f*curPercentage.value,
                useCenter = false,
                style = Stroke(
                    strokeWidth.toPx(),
                    cap = StrokeCap.Round
                )
            )
        }
        val valuenumber = curPercentage.value * number
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text(
                text = heading,
                color = Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = valuenumber.toInt().toString(),
                color = Color.Black,
                fontSize = fontSize,
                fontWeight = FontWeight.Bold
            )
        }

    }
}


@OptIn(ExperimentalPagerApi::class)
@ExperimentalPagerApi // 1.
@Composable
fun SwipingScreen() {

    val pageState = rememberPagerState()

    Column {

        HorizontalPager(
            count = 4,
            state = pageState,
            modifier = Modifier
                .fillMaxSize(1f)
        ) { page ->
            Slaves(slave = dataList[page], (page + 1).toString())
        }
        HorizontalPagerIndicator(
            pagerState = pageState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(20.dp)
        )

    }
}

