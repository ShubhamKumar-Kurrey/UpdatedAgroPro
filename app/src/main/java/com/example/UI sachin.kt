package com.example

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import java.util.*


@Composable
fun Base (){
    Box(modifier = Modifier
            .fillMaxSize()
            .background(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFFD70A84),
                    Color(0xFF120A83)
                ),
            ),
        )

    )

}



