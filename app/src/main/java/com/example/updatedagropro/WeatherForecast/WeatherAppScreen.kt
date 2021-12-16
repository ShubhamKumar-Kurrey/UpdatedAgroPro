package com.example.updatedagropro.WeatherForecast

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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

@Composable
fun WeatherPage() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderImage()
        MainInfo()
        InfoTable()
    }
}

@Composable
fun HeaderImage() {
    Icon(
        painter = painterResource(id = R.drawable.snow ),
        contentDescription = null,
        modifier = Modifier.width(50.dp).size(50.dp),
        tint= Color.Blue
    )
}

@Composable
fun MainInfo() {
    Column(
        modifier = Modifier.padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "17°", color = Color.Blue, fontSize = 48.sp, fontWeight = FontWeight.Bold)
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
fun InfoTable() {
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
                subtitle = "12 m/s",
                modifier = Modifier.weight(
                    1f
                )
            )
            InfoItem(
                iconRes = R.drawable.wind,
                title = "Humidty",
                subtitle = "31%",
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