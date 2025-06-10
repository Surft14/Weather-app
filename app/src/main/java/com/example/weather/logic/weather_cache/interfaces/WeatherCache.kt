package com.example.weather.logic.weather_cache.interfaces

import android.content.Context
import androidx.compose.runtime.MutableState
import com.example.weather.data.model.WeatherInfo

interface WeatherCache {
    suspend fun clearStaleWeatherData(context: Context)

    suspend fun readWeatherData(context: Context, day: MutableState<WeatherInfo>, dayList: MutableState<List<WeatherInfo>>)
    suspend fun readUserCity(context: Context, city: MutableState<String>)

    suspend fun saveCity(city: String, context: Context)
    suspend fun saveWeatherData(weatherDataJSON: String, context: Context)
}