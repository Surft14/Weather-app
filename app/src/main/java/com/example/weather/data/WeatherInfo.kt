package com.example.weather.data

data class WeatherInfo(
    var city: String,
    var time: String,
    var temp: String,
    var feelLike: String,
    var tempMax: Double,
    var tempMin: Double,
    var wind: Double,
    var hours: String,
    var weather: String,
    var icon: String,
    var lat: Double,
    var lon: Double,
)
