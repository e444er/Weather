package com.moon.network.data.api

import com.moon.network.data.model.Forecast
import com.moon.network.data.model.Forecastday
import com.moon.network.data.model.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("current.json?key=5516fb8002b44faea85131006230606")
    suspend fun getLondon(
        @Query("q") q: String,
        @Query("aqi") aqi: String = "no",
    ): Weather

    @GET("https://api.weatherapi.com/v1/forecast.json?key=5516fb8002b44faea85131006230606")
    suspend fun getDays(
        @Query("q") q: String,
        @Query("aqi") aqi: String = "yes",
        @Query("days") days: String,
    ): Weather
}