package com.example.weather.weather

import com.example.weather.data.WeatherInfo
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

fun getWeatherInfoByHours(hours: String): List<WeatherInfo>{
    if (hours.isEmpty())return listOf()
    val hoursArray = JSONArray(hours)
    val list  = ArrayList<WeatherInfo>()
    for (i in 0 until hoursArray.length()){
        val item = hoursArray[i] as JSONObject
        list.add(
            WeatherInfo(
                time = item.getString("time"),
                temp = item.getString("temp_c").toFloat().toInt().toString(),
                feelLike = item.getString("feelslike_c").toFloat().toInt().toString(),
                weather = item.getJSONObject("condition").getString("text"),
                wind = item.getString("wind_kph").toFloat().toInt().toString(),
                windDir = item.getString("wind_dir"),
                icon = item.getJSONObject("condition").getString("icon"),
            )
        )
    }
    return list
}