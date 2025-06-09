package com.example.weather.data.model

data class WeatherHour(

    var city: String,

    var date: String,
    var time: String,

    var temp: String,
    var feelLike: String,
    var speed: String,
    var dir: String,

    var text: String,
    var icon: String,
    var code: Int,
)
