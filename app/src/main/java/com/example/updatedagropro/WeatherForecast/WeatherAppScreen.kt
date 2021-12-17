package com.example.updatedagropro.WeatherForecast

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.updatedagropro.R
import com.example.updatedagropro.Sensor
import com.example.updatedagropro.Slave_Name
import com.example.updatedagropro.network.API
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.util.*
import kotlin.concurrent.fixedRateTimer

@Composable
fun WeatherPage() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderImage()
        MainInfo(slaveId = 1.toString())
        InfoTable(slaveId = 1.toString())
    }
}

@Composable
fun HeaderImage() {
    Icon(
        painter = painterResource(id = R.drawable.sunfull),
        contentDescription = null,
        modifier = Modifier
            .width(50.dp)
            .size(50.dp),
        tint= Color.Blue
    )
}


@Composable
fun InfoTable(slaveId: String) {
    var am by remember {
        mutableStateOf(0f)
    }
    var ws by remember {
        mutableStateOf(0f)
    }


    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit) {
        fixedRateTimer("observer", startAt = Date(), period = 1000) {
            scope.launch(Dispatchers.IO) {
                withTimeout(10000) {
                    val res = API.getSensorData(slaveId)
                    val reading = res.getOrNull()
                    if(reading != null){
                        am = reading.ah
                        ws = reading.ws

                    }
                }
            }

        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(
                Color.LightGray
            )
    ) {
        Row(Modifier.padding(16.dp)) {
            InfoItem(
                iconRes = R.drawable.wind,
                title = "Wind Speed",
                subtitle = ws.toString()+"m/s",
                modifier = Modifier.weight(
                    1f
                )
            )
            InfoItem(
                iconRes = R.drawable.wind,
                title = "Humidty",
                subtitle = am.toString()+"%",
                modifier = Modifier.weight(
                    1f
                )
            )
        }
        Divider(color = Color.Gray, modifier = Modifier.padding(horizontal = 16.dp))
        Row(Modifier.padding(16.dp)) {
            InfoItem(
                iconRes = R.drawable.wind,
                title = "Min Temp",
                subtitle = "12° C",
                modifier = Modifier.weight(
                    1f
                )
            )
            InfoItem(
                iconRes = R.drawable.wind,
                title = "Max Temp",
                subtitle = "16° C",
                modifier = Modifier.weight(
                    1f
                )
            )
        }
    }
}

@Composable
fun MainInfo(slaveId: String) {
    var at by remember {
        mutableStateOf(0f)
    }
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit) {
        fixedRateTimer("observer", startAt = Date(), period = 1000) {
            scope.launch(Dispatchers.IO) {
                withTimeout(10000) {
                    val res = API.getSensorData(slaveId)
                    val reading = res.getOrNull()
                    if(reading != null){
                        at = reading.at
                    }
                }
            }
        }
    }
    Column(
        modifier = Modifier.padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = at.toString()+"°", color = Color.Blue, fontSize = 48.sp, fontWeight = FontWeight.Bold)
        Text(
            text = "Bhuli, Dhanbad",
            color = Color.Blue,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = "Today cloud is clear.\nVery less chance of Rain",
            color = Color.Black,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 24.dp)
        )
    }
}



@Composable
fun InfoItem(@DrawableRes iconRes: Int, title: String, subtitle: String, modifier: Modifier) {
    Row(modifier = modifier) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 8.dp)
                .width(40.dp)
        )
        Column {
            Text(title, color = Color.Blue)
            Text(subtitle, color = Color.Blue, fontWeight = FontWeight.Bold)
        }
    }
}