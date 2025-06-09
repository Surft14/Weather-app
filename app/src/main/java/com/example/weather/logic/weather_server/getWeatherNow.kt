package com.example.weather.logic.weather_server

import android.util.Log
import com.example.weather.data.model.WeatherNow
import org.json.JSONObject

fun getWeatherNow(response: String): WeatherNow {
    Log.d("MyLog", "getWeatherNow Start")
    if (response.isEmpty()) return WeatherNow(
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
        val mainOdj = JSONObject(response)

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