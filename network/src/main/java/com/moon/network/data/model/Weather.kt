package com.moon.network.data.model

data class Weather(
    val current: Current,
    val location: Location,
    val forecast: Forecast
)