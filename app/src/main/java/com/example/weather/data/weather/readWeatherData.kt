package com.example.weather.data.weather

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.example.weather.data.WeatherInfo
import com.example.weather.data.const.PreferencesKey
import com.example.weather.data.dataStore
import com.example.weather.logic.weather.getWeatherInfoByDays
import kotlinx.coroutines.flow.first

suspend fun readWeatherData(context: Context, day: MutableState<WeatherInfo>, dayList: MutableState<List<WeatherInfo>>) {
    val preferences = context.dataStore.data.first()
    val weatherData = preferences[PreferencesKey.WEATHER_DATA_KEY]
    if (weatherData != null){
        val list = getWeatherInfoByDays(weatherData)
        day.value = list[0]
        dayList.value = list
    }
}

suspend fun readUserCity(context: Context, city: MutableState<String>){
    val preferences = context.dataStore.data.first()
    val cityPref = preferences[PreferencesKey.USER_CITY_KEY]
    if (cityPref != null){
        Log.d("MyLog", "readUserCity city: $cityPref")
        city.value = cityPref
    }
}