package com.moon.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

//https://api.weatherapi.com/v1/forecast.json?key=70a169ee0d3a4bfa9b773350230606&q=London&aqi=yes&days=13
//https://api.weatherapi.com/v1/current.json?key=70a169ee0d3a4bfa9b773350230606&q=London&aqi=no