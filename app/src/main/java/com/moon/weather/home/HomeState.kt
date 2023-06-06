package com.moon.weather.home

import com.moon.network.data.model.Weather

data class HomeState(
    val weather: Weather? = null,
    val error: String = "",
    val isLoading: Boolean = false
)