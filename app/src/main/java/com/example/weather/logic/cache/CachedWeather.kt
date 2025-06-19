package com.example.weather.logic.cache

object CachedWeather {
    var cityName: String? = null
    var weatherJSON: String? = null
    var imageBase64: String? = null
    lateinit var skyboxHash: String
}