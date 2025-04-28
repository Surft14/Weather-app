package com.example.weather.data.const

import androidx.datastore.preferences.core.stringPreferencesKey

object Const{
    const val API_KEY_FW = "e484c70c78e84b779ab151237251002"//https://www.weatherapi.com/
    const val API_KEY_OWM = "1a86c614dfa6db6e65f778d79fe2e131"//https://openweathermap.org/
    const val UNITS = "metric"
    const val LANGUAGE = "ru"
}

object PreferencesKey{
    val WEATHER_DATA_KEY = stringPreferencesKey("weatherData")
    val USER_CITY_KEY = stringPreferencesKey("userCity")
}
