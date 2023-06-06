package com.moon.network.data.model

data class Forecastday(
    val date: String,
    val date_epoch: Int,
    val day: Day,
    val hour: List<Hour>,
    val condition: Condition,
)