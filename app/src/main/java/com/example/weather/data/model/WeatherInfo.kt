package com.example.weather.data.model

data class WeatherInfo(
    var city: String = "",
    var time: String = "",

    var temp: String = "",
    var feelLike: String = "",
    var tempMax: String = "",
    var tempMin: String = "",
    var wind: String = "",
    var windDir: String = "",

    var hours: String = "",
    var weather: String = "",
    var icon: String = "",
    var code: Int = 0
)