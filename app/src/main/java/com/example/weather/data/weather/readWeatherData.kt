package com.example.weather.data.weather

import android.content.Context
import androidx.compose.runtime.MutableState
import com.example.weather.data.WeatherInfo
import com.example.weather.data.const.PreferencesKey
import com.example.weather.data.dataStore
import com.example.weather.logic.weather.getWeatherInfoByDays
import kotlinx.coroutines.flow.first

suspend fun readWeatherData(context: Context, city: MutableState<String>, day: MutableState<WeatherInfo>, dayList: MutableState<List<WeatherInfo>>) {
    val preferences = context.dataStore.data.first()
    val weatherData = preferences[PreferencesKey.WEATHER_DATA_KEY]
    val cityPref = preferences[PreferencesKey.USER_CITY_KEY]
    if (weatherData != null){
        val list = getWeatherInfoByDays(weatherData)
        day.value = list[0]
        dayList.value = list
    }
    if (cityPref != null){
        city.value = cityPref
    }
}