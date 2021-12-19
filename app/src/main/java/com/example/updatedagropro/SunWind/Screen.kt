package com.example.updatedagropro.SunWind

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.updatedagropro.Sensor
import com.example.updatedagropro.network.API
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.util.*
import kotlin.concurrent.fixedRateTimer

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Sun_Wind() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(1f)
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            ScreenWindSun()
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@ExperimentalPagerApi // 1.
@Composable
fun ScreenWindSun() {

    val pageState = rememberPagerState()

    Column {

        HorizontalPager(
            count = 1,
            state = pageState,
            modifier = Modifier
                .fillMaxSize(1f)
        ) { exer ->
            SunWindReading(exer.toString())
        }
        HorizontalPagerIndicator(
            pagerState = pageState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(20.dp)
        )

    }
}

@Composable
fun SunWindReading(slaveId: String) {
    var ws by remember {
        mutableStateOf(0f)
    }
    var sl by remember {
        mutableStateOf(0f)
    }
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit) {
        fixedRateTimer("observer", startAt = Date(), period = 1000) {
            scope.launch(Dispatchers.IO) {
                withTimeout(1000) {
                    val res = API.getSensorData(slaveId)
                    val reading = res.getOrNull()
                    if(reading != null){
                        ws = reading.ws
                        sl = reading.sl
                    }
                }
            }

        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Sun & Wind", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.Blue)
        Spacer(modifier = Modifier.padding(vertical = 25.dp))
        circlebarSunWind(percentage = sl, symbol = "%", sensorName ="Sun Light\n Intensity", sensorReadingValue = sl )
        circlebarSunWind(percentage = ws, symbol = " m/s", sensorName ="Wind Speed", sensorReadingValue = ws )
    }

}
