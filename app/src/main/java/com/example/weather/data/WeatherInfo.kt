package com.example.weather.data

data class WeatherInfo(
    var city: String = "",
    var time: String = "",
    var temp: String = "",
    var feelLike: String = "",
    var tempMax: Double = 0.0,
    var tempMin: Double = 0.0,
    var weather: String = "",
    var wind: Double = 0.0,
    var windDir: String = "",
    var hours: String = "",
    var icon: String = "",
)
