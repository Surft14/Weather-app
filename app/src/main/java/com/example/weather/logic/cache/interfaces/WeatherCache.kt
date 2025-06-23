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
    suspend fun readIsFahrenheit(context: Context): Boolean?
    suspend fun readIsMile(context: Context): Boolean?

    suspend fun saveCity(city: String, context: Context)
    suspend fun saveWeatherData(weatherDataJSON: String, context: Context)
    suspend fun saveBase64(image: String, context: Context)
    suspend fun saveIsMile(isMile: Boolean, context: Context)
    suspend fun saveIsFahrenheit(isFahrenheit: Boolean, context: Context)
}