package com.example.updatedagropro

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.paging.Pager

data class Slave_Name(
    val name:String,
)
    val dataList = listOf(
        Slave_Name(name = "Slave 1"),
        Slave_Name(name = "Slave 2"),
        Slave_Name(name = "Slave 3"),
        Slave_Name(name = "Slave 4")
    )
