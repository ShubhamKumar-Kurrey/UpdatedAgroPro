package com.example.updatedagropro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.updatedagropro.pHscale.circularProgressBar
import com.example.updatedagropro.pHscale.pHcircularBar
import com.example.updatedagropro.ui.theme.UpdatedAgroProTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UpdatedAgroProTheme {
               AppMainScreen()
            // circularProgressBar(percentage = 11f, heading = "pH", number = 1)
             //   pHcircularBar(percentage = 13f, sensorReadingValue = 13f, symbol = "pH", sensorName = "pH Sensor")
            }
        }
    }
}
