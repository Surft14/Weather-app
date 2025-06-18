package com.example.weather.logic.repository.weather.impl

import android.content.Context
import android.util.Log
import com.example.weather.data.const.WeatherCodes
import com.example.weather.data.model.WeatherForecast
import com.example.weather.data.model.WeatherHour
import com.example.weather.data.model.WeatherInfo
import com.example.weather.data.model.WeatherNow
import com.example.weather.logic.cache.impl.WeatherCacheImpl
import com.example.weather.logic.cache.interfaces.WeatherCache
import com.example.weather.logic.network.impl.WeatherServerImpl
import com.example.weather.logic.network.interfaces.WeatherServer
import com.example.weather.logic.repository.weather.interfaces.WeatherRepository

class WeatherRepositoryImpl(
    private val weatherCache: WeatherCache = WeatherCacheImpl(),
    private val weatherServer: WeatherServer = WeatherServerImpl(),
) : WeatherRepository {
    override suspend fun fetchWeatherJSON(
        city: String,
        context: Context,
    ): String? {
        Log.d("MyLog", "repository fetchWeatherJSON start")
        val json = weatherServer.fetchWeatherJSON(city, context)
        if (json != null) {
            weatherCache.saveWeatherData(json, context)
        }
        return json
    }

    override suspend fun parseWeatherNow(json: String): WeatherNow {
        Log.d("MyLog", "repository parseWeatherNow start")
        return weatherServer.parseWeatherNow(json)
    }

    override suspend fun parseWeatherHour(json: String): List<WeatherHour> {
        Log.d("MyLog", "repository parseWeatherHour start")
        return weatherServer.parseWeatherHour(json)
    }

    override suspend fun parseWeatherForecast(json: String): List<WeatherForecast> {
        Log.d("MyLog", "repository parseWeatherForecast start")
        return weatherServer.parseWeatherForecast(json)
    }

    override suspend fun parseWeather(json: String, context: Context): WeatherInfo {
        Log.d("MyLog", "repository parseWeather start")
        return WeatherInfo(
            weatherNow = parseWeatherNow(json),
            listWeatherHour = parseWeatherHour(json),
            listWeatherForecast = parseWeatherForecast(json)
        )
    }

    override suspend fun fetchAndParseWeather(city: String, context: Context): WeatherInfo {
        Log.d("MyLog", "repository fetchAndParseWeather start")
        val json = fetchWeatherJSON(city, context)
        if (!json.isNullOrEmpty()){ weatherCache.saveWeatherData(json.toString(), context) }
        val weatherInfo = WeatherInfo()
        if (json != null) {
            weatherInfo.weatherNow = parseWeatherNow(json)
            weatherInfo.listWeatherHour = parseWeatherHour(json)
            weatherInfo.listWeatherForecast = parseWeatherForecast(json)
        }

        return weatherInfo
    }

    override suspend fun searchAndParseWeather(city: String, context: Context): WeatherInfo {
        Log.d("MyLog", "repository searchAndParseWeather start")
        val json = fetchWeatherJSON(city, context)
        val weatherInfo = WeatherInfo()
        if (json != null) {
            weatherInfo.weatherNow = parseWeatherNow(json)
            weatherInfo.listWeatherHour = parseWeatherHour(json)
            weatherInfo.listWeatherForecast = parseWeatherForecast(json)
        }

        return weatherInfo
    }

    override suspend fun clearStaleWeatherData(context: Context) {
        Log.d("MyLog", "repository clearStaleWeatherData start")
        weatherCache.clearStaleWeatherData(context)
    }

    override suspend fun readWeatherData(context: Context): String? {
        Log.d("MyLog", "repository readWeatherData start")
        return weatherCache.readWeatherData(context)
    }

    override suspend fun readUserCity(context: Context): String? {
        Log.d("MyLog", "repository readUserCity start")
        return weatherCache.readUserCity(context)
    }

    override suspend fun saveCity(city: String, context: Context) {
        Log.d("MyLog", "repository saveCity start")
        return weatherCache.saveCity(city, context)
    }

    override suspend fun saveWeatherData(
        weatherDataJSON: String,
        context: Context,
    ) {
        Log.d("MyLog", "repository saveWeatherData start")
        return weatherCache.saveWeatherData(weatherDataJSON, context)
    }

    override suspend fun getWeatherCondition(day: WeatherInfo?): String {
        Log.d("MyLog", "repository getWeatherCondition start")
        Log.i("MyLog", "repository getWeatherCondition info: $day")

        return weatherServer.getWeatherCondition(day)
    }

}