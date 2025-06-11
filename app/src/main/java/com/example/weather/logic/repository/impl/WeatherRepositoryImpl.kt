package com.example.weather.logic.repository.impl

import android.content.Context
import com.example.weather.data.model.WeatherForecast
import com.example.weather.data.model.WeatherHour
import com.example.weather.data.model.WeatherInfo
import com.example.weather.data.model.WeatherNow
import com.example.weather.logic.cache.impl.WeatherCacheImpl
import com.example.weather.logic.cache.interfaces.WeatherCache
import com.example.weather.logic.network.impl.WeatherServerImpl
import com.example.weather.logic.network.interfaces.WeatherServer
import com.example.weather.logic.repository.interfaces.WeatherRepository

class WeatherRepositoryImpl(
    private val weatherCache: WeatherCache = WeatherCacheImpl(),
    private val weatherServer: WeatherServer = WeatherServerImpl()
) : WeatherRepository {


    override suspend fun fetchWeatherJSON(
        city: String,
        context: Context,
    ): String? {
        val json = weatherServer.fetchWeatherJSON(city, context)
        if (json != null){ weatherCache.saveWeatherData(json, context) }
        return json
    }

    override suspend fun parseWeatherNow(json: String): WeatherNow {
        return weatherServer.parseWeatherNow(json)
    }
    override suspend fun parseWeatherHour(json: String): List<WeatherHour> {
        return weatherServer.parseWeatherHour(json)
    }
    override suspend fun parseWeatherForecast(json: String): List<WeatherForecast> {
        return weatherServer.parseWeatherForecast(json)
    }

    override suspend fun fetchAndParseWeather(city: String, context: Context): WeatherInfo{
        val json = fetchWeatherJSON(city, context)
        val weatherInfo : WeatherInfo = WeatherInfo()
        if (json != null){
            weatherInfo.weatherNow = parseWeatherNow(json)
            weatherInfo.listWeatherHour = parseWeatherHour(json)
            weatherInfo.listWeatherForecast = parseWeatherForecast(json)
        }

        return weatherInfo
    }

    override suspend fun clearStaleWeatherData(context: Context) {
        weatherCache.clearStaleWeatherData(context)
    }

    override suspend fun readWeatherData(context: Context): String? {
        return weatherCache.readWeatherData(context)
    }
    override suspend fun readUserCity(context: Context): String? {
        return weatherCache.readUserCity(context)
    }

    override suspend fun saveCity(city: String, context: Context) {
        return weatherCache.saveCity(city, context)
    }
    override suspend fun saveWeatherData(
        weatherDataJSON: String,
        context: Context,
    ) {
        return weatherCache.saveWeatherData(weatherDataJSON, context)
    }

}