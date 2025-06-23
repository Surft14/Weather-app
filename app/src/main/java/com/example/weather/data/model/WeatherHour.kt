package com.example.weather.data.model

data class WeatherHour(

    var city: String = "",

    var date: String = "",
    var time: String = "",

    var temp: String = "",
    var tempF: String = "",
    var feelLike: String = "",
    var feelLikeF: String = "",
    var speed: String = "",
    var speedM: String = "",
    var dir: String = "",

    var text: String = "",
    var icon: String = "",
    var code: Int = 0,
)
