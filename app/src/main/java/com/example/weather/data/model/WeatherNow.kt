package com.example.weather.data.model

data class WeatherNow(

    var city: String = "",
    var region: String = "",
    var country: String = "",

    var dateTime: String = "",
    var lastDateTime: String = "",

    var temp: String = "",
    var feelLike: String = "",
    var speed: String = "",
    var dir: String = "",

    var icon: String = "",
    var text: String = "",
    var back: String = "",
    var code: Int = 0,
)
