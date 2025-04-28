package com.example.weather.logic.weather

import android.util.Log
import com.example.weather.data.WeatherInfo
import org.json.JSONObject

fun getWeatherInfoByDays(response: String): List<WeatherInfo>{
    Log.d("MyLog", "getWeatherInfoByDays Start")
    if (response.isEmpty()) return listOf()
    val list = ArrayList<WeatherInfo>()
    val mainObj = JSONObject(response)
    val city = mainObj.getJSONObject("location").getString("name")
    val days = mainObj
        .getJSONObject("forecast")
        .getJSONArray("forecastday")
    for (i in 0 until days.length()){
        val item = days[i] as JSONObject
        list.add(
            try{
                WeatherInfo(
                    city,
                    time = item.getString("date"),
                    temp = "",
                    feelLike = "",
                    tempMax = mainObj
                        .getJSONObject("forecast")
                        .getJSONArray("forecastday")
                        .getJSONObject(0)
                        .getJSONObject("day")
                        .getDouble("maxtemp_c").toFloat().toInt().toString(),
                    tempMin = mainObj
                        .getJSONObject("forecast")
                        .getJSONArray("forecastday")
                        .getJSONObject(0)
                        .getJSONObject("day")
                        .getDouble("mintemp_c").toFloat().toInt().toString(),
                    weather = mainObj
                        .getJSONObject("current")
                        .getJSONObject("condition")
                        .getString("text"),
                    wind = "",
                    windDir = "",
                    hours = mainObj
                        .getJSONObject("forecast")
                        .getJSONArray("forecastday")
                        .getJSONObject(i)
                        .getJSONArray("hour").toString(),
                    icon = mainObj
                        .getJSONObject("current")
                        .getJSONObject("condition")
                        .getString("icon"),
                    )
            } catch (e: Exception){
                Log.e("MyLog", " getWeatherInfoByDays Error: $e")
            } as WeatherInfo
        )
        Log.i("MyLog", "Hour in getWeatherInfoByDays: ${list[i].hours}")
    }
    list[0] = list[0].copy(
        time = mainObj.getJSONObject("current").getString("last_updated"),
        temp = mainObj.getJSONObject("current").getString("temp_c").toFloat().toInt().toString(),
        feelLike = mainObj.getJSONObject("current").getString("feelslike_c").toFloat().toInt().toString(),
        wind = mainObj.getJSONObject("current").getDouble("wind_kph").toFloat().toInt().toString(),
        weather = mainObj
            .getJSONObject("current")
            .getJSONObject("condition")
            .getString("text"),
        windDir = mainObj.getJSONObject("current").getString("wind_dir"),
        icon = mainObj
            .getJSONObject("current")
            .getJSONObject("condition")
            .getString("icon"),
    )
    Log.d("MyLog", "getWeatherInfoByDays End")
    return list
}