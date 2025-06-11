package com.example.weather.data.model

import java.util.ArrayList

data class WeatherInfo(
    var weatherNow: WeatherNow = WeatherNow(),
    var listWeatherHour: List<WeatherHour> = ArrayList<WeatherHour>(),
    var listWeatherForecast: List<WeatherForecast> = ArrayList<WeatherForecast>()
)