package com.example.weather.logic.network.impl

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weather.data.model.WeatherForecast
import com.example.weather.data.model.WeatherHour
import com.example.weather.data.model.WeatherNow
import com.example.weather.logic.network.CustomHurlStack
import com.example.weather.logic.network.interfaces.WeatherServer
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class WeatherServerImpl() : WeatherServer {

    override suspend fun fetchWeatherJSON(
        city: String,
        context: Context,
    ) : String? = suspendCoroutine { continuation ->
        Log.d("MyLog", "server fetchWeatherJSON start")
        val requestQueue = Volley.newRequestQueue(context, CustomHurlStack(context))

        val url = "https://192.168.0.122:8443/api/v1/weathers_now/get/db/city/weather_now?city=$city"
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                Log.i("MyLog", "server fetchWeatherJSON info: $response")
                continuation.resume(response)
            },
            { err ->
                Log.e("MyLog", "Get data from weather-server error: ", err)
                continuation.resume(null)
            }
        )
        requestQueue.add(stringRequest)
        Log.i("MyLog", "server fetchWeatherJSON info: $stringRequest")
    }

    override suspend fun parseWeatherHour(json: String): List<WeatherHour> {
        Log.d("MyLog", "server parseWeatherHour start")
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
        Log.i("MyLog", "server parseWeatherHour info: ${listWeatherHour[1]}")
        return listWeatherHour
    }

    override suspend fun parseWeatherNow(json: String): WeatherNow {
        Log.d("MyLog", "server parseWeatherNow Start")
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
            Log.e("MyLog", "server parseWeatherNow Error: $e")
        }
        Log.i("MyLog", "server parseWeatherNow info: $weatherNow")
        return weatherNow
    }

    override suspend fun parseWeatherForecast(json: String): List<WeatherForecast> {
        if (json.isEmpty()) return listOf()
        Log.d("MyLog", "server parseWeatherForecast start")

        val listWeathers = ArrayList<WeatherForecast>()
        val mainOdj = JSONObject(json)

        val weathers = mainOdj.getJSONArray("weathersList")

        for (i in 0 until weathers.length()){
            val weathersItem = weathers[i] as JSONObject

            listWeathers.add(
                WeatherForecast(
                    city = weathersItem.getString("city"),
                    date = weathersItem.getString("date"),
                    avgTemp = weathersItem.getString("avgTemp"),
                    maxWind = weathersItem.getString("maxWind"),
                    text = weathersItem.getString("text"),
                    icon = weathersItem.getString("icon"),
                    code = weathersItem.getInt("code")
                )
            )

        }
        Log.i("MyLog", "server parseWeatherForecast info: ${listWeathers[1]}")
        return listWeathers
    }
}