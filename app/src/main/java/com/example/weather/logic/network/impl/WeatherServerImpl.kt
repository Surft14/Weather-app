package com.example.weather.logic.network.impl

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
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
        //192.168.0.122
        //85.234.7.243
        val url = "https://85.234.7.243:8443/api/v1/weathers_now/get/db/city/weather_now?city=$city"
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

        val policy = DefaultRetryPolicy(
            15000, // 10 секунд таймаут
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        stringRequest.setRetryPolicy(policy)
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
            val mainObj = JSONObject(json)

            weatherNow.city = mainObj.getString("city")
            weatherNow.region = mainObj.getString("region")
            weatherNow.country = mainObj.getString("country")

            val rawDate = mainObj.getString("dateTime")
            val formattedDate = rawDate.replace("T", " ")
            weatherNow.dateTime = formattedDate
            weatherNow.lastDateTime = mainObj.getString("lastUpdateTime")

            weatherNow.temp = mainObj.getString("temp")
            weatherNow.feelLike = mainObj.getString("feelLike")
            weatherNow.dir = mainObj.getString("dir")
            weatherNow.speed = mainObj.getString("speed")

            weatherNow.text = mainObj.getString("text")
            weatherNow.icon = mainObj.getString("icon")
            weatherNow.code = mainObj.getInt("code")
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