package com.example.weather.logic.cache

object CachedWeather {
    var cityName: String? = null
    var weatherJSON: String? = null
    var imageBase64: String? = null
    var isMile: Boolean? = false
    var isFahrenheit: Boolean? = false
    lateinit var skyboxHash: String
}