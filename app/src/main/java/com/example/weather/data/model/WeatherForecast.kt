package com.example.weather.data.model

data class WeatherForecast(

    var city: String = "",

    var avgTemp: String = "",
    var avgTempF: String = "",
    var maxWind: String = "",
    var maxWindM: String = "",

    var text: String = "",
    var icon: String = "",
    var code: Int = 0,
    val date: String = "",
)


