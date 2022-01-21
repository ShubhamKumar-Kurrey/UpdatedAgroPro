package com.example.updatedagropro

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.core.graphics.component1
import androidx.core.graphics.component2
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
            .background(Color(0xFFE6E6E6)),
        contentAlignment = Alignment.Center
    ) {
        SwipingScreen(Modifier.background(Color(0xFFE2DCF5)))
    }
}

@Composable
fun Sensor(Value: Float, headingname: String, symbolType: String) {
    Box() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

        ) {
            textofBar(pe = Value, sn= headingname, sy=symbolType, sv = Value )
            //Spacer(modifier = Modifier.padding(5.dp))
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
                withTimeout(1000) {
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
            .padding(horizontal = 25.dp)
            .padding(vertical=40.dp)
            .fillMaxWidth(1f)
            .fillMaxHeight(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = slave.name, fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.padding(vertical = 15.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(1f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF120A83))
                    .clickable { //navController.navigate(Screen.DetailsScreen.route)
                    }
            ) {
                Sensor(Value = st, headingname = "Soil Temperature", symbolType = "°C")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF120A83))
                    .clickable { //navController.navigate(Screen.DetailsScreen.route)
                    }
            ) {
                Sensor(Value = sm, headingname = "Soil Moisture", symbolType = "%")
            }
        }
        Spacer(modifier = Modifier.padding(vertical = 30.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF120A83))
                    .clickable { //navController.navigate(Screen.DetailsScreen.route)
                    }
            )
            {
                Sensor(Value = at, headingname = "Air Temperature", symbolType = "°C")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF120A83))
                    .clickable { //navController.navigate(Screen.DetailsScreen.route)
                    }
            )
            {
                Sensor(Value = am, headingname = "Humidity", symbolType = "%")
            }
        }
    }

}




@OptIn(ExperimentalPagerApi::class)
@ExperimentalPagerApi // 1.
@Composable
fun SwipingScreen(
     modifier : Modifier
) {

    val pageState = rememberPagerState()

    Column() {
        HorizontalPager(
            modifier = Modifier
                .fillMaxSize(1f).background(color=Color(0xFF35207B)),
            count = 4,
            state = pageState
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
