package com.example.weather.logic.cache.interfaces

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import com.example.weather.data.model.WeatherInfo

interface WeatherCache {
    suspend fun clearStaleWeatherData(context: Context)

    suspend fun readWeatherData(context: Context) : String?
    suspend fun readUserCity(context: Context) : String?
    suspend fun readBase64(context: Context): String?

    suspend fun saveCity(city: String, context: Context)
    suspend fun saveWeatherData(weatherDataJSON: String, context: Context)
    suspend fun saveBase64(image: String, context: Context)
}