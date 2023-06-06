package com.moon.weather.detail

import com.moon.network.data.model.Weather

data class DetailState(
    val weather: Weather? = null,
    val error: String = "",
    val isLoading: Boolean = false
)