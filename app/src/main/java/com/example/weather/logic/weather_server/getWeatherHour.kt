package com.example.weather.logic.weather_server

import android.util.Log
import com.example.weather.data.model.WeatherHour
import org.json.JSONObject

fun getWeatherHour(response: String): List<WeatherHour> {
    Log.d("MyLog", "getWeatherHour start")

    val listWeatherHour : ArrayList<WeatherHour> = ArrayList<WeatherHour>()
    val mainObj = JSONObject(response)
    val listHour = mainObj.getJSONArray("listHour")

    for(i in 0 until listHour.length() ){
        val hour = listHour[i] as JSONObject

        listWeatherHour.add(
            WeatherHour(
                city = mainObj.getString("city"),

                time = mainObj.getString("time"),
                date = mainObj.getString("date"),

                temp = mainObj.getString("temp"),
                feelLike = mainObj.getString("feelLike"),

                icon = mainObj.getString("icon"),
                text = mainObj.getString("text"),
                code = mainObj.getInt("code"),

                dir = mainObj.getString("dir"),
                speed = mainObj.getString("speed")
            )
        )

    }

    return listWeatherHour

}