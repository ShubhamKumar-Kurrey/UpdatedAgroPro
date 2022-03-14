package com.example.updatedagropro.Range

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.core.util.toRange
import kotlin.math.roundToInt

@Composable
fun phCalculator() {
    SliderComposable()
}

@Composable
fun MySliderDemo() {
    Box(contentAlignment = Alignment.Center) {
        var sliderPosition by remember { mutableStateOf(0f) }
        Text(text = sliderPosition.toString())
        Spacer(modifier = Modifier.padding(vertical = 15.dp))
        Slider(value = sliderPosition,
            valueRange = 0f..13f,
            onValueChange = { sliderPosition = it })

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SliderComposable() {
    var sliderPosition by remember { mutableStateOf(0f..0.95f) }

    Column() {
        RangeSlider(
            values = sliderPosition,
            onValueChange = { sliderPosition = it },
            valueRange = 0f..100f,
            onValueChangeFinished = {
                // launch some business logic update with the state you hold
                // viewModel.updateSelectedSliderValue(sliderPosition)
            },
            steps = 10,
            colors = SliderDefaults.colors(
                thumbColor = Color.Red,
                activeTrackColor = Color.Blue,
                inactiveTrackColor = Color.Green
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "Range ${(sliderPosition.toRange().lower).roundToInt()}"+
                "Range ${(sliderPosition.toRange().upper).roundToInt()}"
        )
    }

}
