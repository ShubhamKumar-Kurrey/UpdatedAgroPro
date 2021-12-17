package com.example.updatedagropro.network

data class SensorData(
    val at: Float = 0f,
    val ah: Float = 0f,
    val st: Float = 0f,
    val sm: Float = 0f,
    val ws: Float = 0f,
    val sl: Float = 0f

)

data class SunWindData(
    val sl: Float=0f,
    val ws: Float=0f
)