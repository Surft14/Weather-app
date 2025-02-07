package com.example.weather.weather

data class WeatherInfo(
    val unk: String = "unknown",
    var temp: String = unk,
    var feelLike: String = unk,
    var tempMax: Double = 0.0,
    var tempMin: Double = 0.0,
    var weather: String = unk,
    var lat: Double = 0.0,
    var lon: Double = 0.0,
)
