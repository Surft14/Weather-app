package com.example.weather.logic.repository.weather.interfaces

import android.content.Context
import com.example.weather.data.model.WeatherForecast
import com.example.weather.data.model.WeatherHour
import com.example.weather.data.model.WeatherInfo
import com.example.weather.data.model.WeatherNow

interface WeatherRepository {

    suspend fun fetchWeatherJSON(city: String, context: Context): String?

    suspend fun parseWeatherNow(json: String): WeatherNow
    suspend fun parseWeatherHour(json: String): List<WeatherHour>
    suspend fun parseWeatherForecast(json: String): List<WeatherForecast>
    suspend fun parseWeather(json: String): WeatherInfo

    suspend fun fetchAndParseWeather(city: String, context: Context): WeatherInfo
    suspend fun searchAndParseWeather(city: String, context: Context) : WeatherInfo

    suspend fun clearStaleWeatherData(context: Context)

    suspend fun readWeatherData(context: Context): String?
    suspend fun readUserCity(context: Context): String?
    suspend fun readBase64(context: Context): String?
    suspend fun readIsFahrenheit(context: Context): Boolean?
    suspend fun readIsMile(context: Context): Boolean?

    suspend fun saveCity(city: String, context: Context)
    suspend fun saveWeatherData(weatherDataJSON: String, context: Context)
    suspend fun saveBase64(image: String, context: Context)
    suspend fun saveIsMile(isMile: Boolean, context: Context)
    suspend fun saveIsFahrenheit(isFahrenheit: Boolean, context: Context)

    suspend fun getWeatherCondition(day: WeatherInfo?): String
}