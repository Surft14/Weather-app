package com.example.weather.weather

import com.example.weather.data.WeatherInfo
import org.json.JSONObject

fun getWeatherInfoByDays(response: String): List<WeatherInfo> {
    if (response.isEmpty()) return listOf()
    val list = ArrayList<WeatherInfo>()
    val mainObj = JSONObject(response)
    val city = mainObj.getJSONObject("location").getString("name")
    val days = mainObj
        .getJSONObject("forecast")
        .getJSONArray("forecastday")
    for (i in 0 until days.length()) {
        val item = days[i] as JSONObject
        list.add(
            WeatherInfo(
                city,
                time = item.getString("date"),
                temp = "",
                feelLike = "",
                tempMax = mainObj
                    .getJSONObject("day")
                    .getDouble("maxtemp_c"),
                tempMin = mainObj
                    .getJSONObject("day")
                    .getDouble("mintemp_c"),
                weather = mainObj
                    .getJSONObject("day")
                    .getJSONObject("condition")
                    .getString("text"),
                wind = 0.0,
                windDir = "",
                hours = mainObj.getJSONArray("hour").toString(),
                icon = mainObj
                    .getJSONObject("day")
                    .getJSONObject("condition")
                    .getString("icon"),

            )
        )
    }
    list[0] = list[0].copy(
        time = mainObj.getJSONObject("current").getString("last_updated"),
        temp = mainObj.getJSONObject("current").getString("temp_c"),
        feelLike = mainObj.getJSONObject("current").getString("feelslike_c"),
        wind = mainObj.getJSONObject("current").getDouble("wind_kph"),
        weather = mainObj
            .getJSONObject("day")
            .getJSONObject("condition")
            .getString("text"),
        windDir = mainObj.getJSONObject("current").getString("wind_dir"),
        icon = mainObj
            .getJSONObject("day")
            .getJSONObject("condition")
            .getString("icon"),
    )
    return list
}