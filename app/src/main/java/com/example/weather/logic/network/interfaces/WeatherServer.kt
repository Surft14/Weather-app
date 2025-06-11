package com.example.weather.logic.network.interfaces

import android.content.Context
import com.example.weather.data.model.WeatherForecast
import com.example.weather.data.model.WeatherHour
import com.example.weather.data.model.WeatherNow

interface WeatherServer {
    suspend fun fetchWeatherJSON(
        city: String,
        context: Context,
    ): String?

    suspend fun parseWeatherHour(json: String): List<WeatherHour>

    suspend fun parseWeatherNow(json: String): WeatherNow

    suspend fun parseWeatherForecast(json: String): List<WeatherForecast>
}