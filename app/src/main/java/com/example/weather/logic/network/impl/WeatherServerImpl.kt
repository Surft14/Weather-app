package com.example.weather.logic.network.impl

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weather.data.model.WeatherForecast
import com.example.weather.data.model.WeatherHour
import com.example.weather.data.model.WeatherNow
import com.example.weather.logic.network.interfaces.WeatherServer
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class WeatherServerImpl() : WeatherServer {

    override suspend fun fetchWeatherJSON(
        city: String,
        context: Context,
    ) : String? = suspendCoroutine { continuation ->
        Log.d("MyLog", "WeatherFunctionImpl start")
        val url = "http:localhost:8085/api/v1/weathers_now/get/db/city/weather_now?city=$city"
        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                continuation.resume(response)
            },
            { err ->
                Log.e("MyLog", "Get data from weather-server error: ", err)
                continuation.resume(null)
            }
        )
        queue.add(stringRequest)
    }

    override suspend fun parseWeatherHour(json: String): List<WeatherHour> {
        Log.d("MyLog", "getWeatherHour start")
        if (json.isEmpty()) return listOf()

        val listWeatherHour : ArrayList<WeatherHour> = ArrayList<WeatherHour>()
        val mainObj = JSONObject(json)
        val listHour = mainObj.getJSONArray("listHour")

        for(i in 0 until listHour.length() ){
            val hour = listHour[i] as JSONObject

            listWeatherHour.add(
                WeatherHour(
                    city = hour.getString("city"),

                    time = hour.getString("time"),
                    date = hour.getString("date"),

                    temp = hour.getString("temp"),
                    feelLike = hour.getString("feelLike"),

                    icon = hour.getString("icon"),
                    text = hour.getString("text"),
                    code = hour.getInt("code"),

                    dir = hour.getString("dir"),
                    speed = hour.getString("speed")
                )
            )

        }

        return listWeatherHour
    }

    override suspend fun parseWeatherNow(json: String): WeatherNow {
        Log.d("MyLog", "getWeatherNow Start")
        if (json.isEmpty()) return WeatherNow(
            city = "",
            region = "",
            country = "",
            dateTime = "",
            lastDateTime = "",
            temp = "",
            feelLike = "",
            speed = "",
            dir = "",
            icon = "",
            text = "",
            code = 0
        )
        val weatherNow: WeatherNow = WeatherNow(
            city = "",
            region = "",
            country = "",
            dateTime = "",
            lastDateTime = "",
            temp = "",
            feelLike = "",
            speed = "",
            dir = "",
            icon = "",
            text = "",
            code = 0
        )

        try{
            val mainOdj = JSONObject(json)

            weatherNow.city = mainOdj.getString("city")
            weatherNow.region = mainOdj.getString("region")
            weatherNow.country = mainOdj.getString("country")

            weatherNow.dateTime = mainOdj.getString("dateTime")
            weatherNow.lastDateTime = mainOdj.getString("lastUpdateTime")

            weatherNow.temp = mainOdj.getString("temp")
            weatherNow.feelLike = mainOdj.getString("feelLike")
            weatherNow.dir = mainOdj.getString("dir")
            weatherNow.speed = mainOdj.getString("speed")

            weatherNow.text = mainOdj.getString("text")
            weatherNow.icon = mainOdj.getString("icon")
            weatherNow.code = mainOdj.getInt("code")
        }catch (e : Exception){
            Log.e("MyLog", " getWeatherNow Error: $e")
        }

        return weatherNow
    }

    override suspend fun parseWeatherForecast(json: String): List<WeatherForecast> {
        if (json.isEmpty()) return listOf()
        Log.d("MyLog", "getWeathers start")

        val listWeathers = ArrayList<WeatherForecast>()
        val mainOdj = JSONObject(json)

        val weathers = mainOdj.getJSONArray("weathersList")

        for (i in 0 until weathers.length()){
            val weathersItem = weathers[i] as JSONObject

            listWeathers.add(
                WeatherForecast(
                    city = weathersItem.getString("city"),
                    avgTemp = weathersItem.getString("avgTemp"),
                    maxWind = weathersItem.getString("maxWind"),
                    text = weathersItem.getString("text"),
                    icon = weathersItem.getString("icon"),
                    code = weathersItem.getInt("code")
                )
            )

        }

        return listWeathers
    }
}