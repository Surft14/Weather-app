package com.example.weather.logic.weather_cache.impl

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.datastore.preferences.core.edit
import com.example.weather.data.const.Const.WEATHER_TTL_MS
import com.example.weather.data.const.PreferencesKey
import com.example.weather.data.const.PreferencesKey.TIME_MS_KEY
import com.example.weather.data.const.PreferencesKey.WEATHER_DATA_KEY
import com.example.weather.data.dataStore
import com.example.weather.data.model.WeatherInfo
import com.example.weather.logic.weather.getWeatherInfoByDays
import com.example.weather.logic.weather_cache.interfaces.WeatherCache
import kotlinx.coroutines.flow.first

class WeatherCacheImpl : WeatherCache {
    override suspend fun clearStaleWeatherData(context: Context) {
        context.dataStore.edit { prefs ->
            val ts = prefs[TIME_MS_KEY] ?: return@edit
            if (System.currentTimeMillis() - ts > WEATHER_TTL_MS) {
                prefs.remove(WEATHER_DATA_KEY)
                prefs.remove(TIME_MS_KEY)
            }
        }
    }

    override suspend fun readWeatherData(
        context: Context,
        day: MutableState<WeatherInfo>,
        dayList: MutableState<List<WeatherInfo>>,
    ) {
        val preferences = context.dataStore.data.first()
        val weatherData = preferences[PreferencesKey.WEATHER_DATA_KEY]
        val ts = preferences[PreferencesKey.TIME_MS_KEY] ?: 0L
        if (System.currentTimeMillis() - ts <= WEATHER_TTL_MS){
            if (weatherData != null){
                val list = getWeatherInfoByDays(weatherData)
                day.value = list[0]
                dayList.value = list
            }
        } else{
            Log.i("MyLog", "clear data")
            clearStaleWeatherData(context)
        }
    }

    override suspend fun readUserCity(
        context: Context,
        city: MutableState<String>,
    ) {
        val preferences = context.dataStore.data.first()
        val cityPref = preferences[PreferencesKey.USER_CITY_KEY]
        if (cityPref != null){
            Log.d("MyLog", "readUserCity city: $cityPref")
            city.value = cityPref
        }
    }

    override suspend fun saveCity(city: String, context: Context) {
        context.dataStore.edit { pref ->
            pref[PreferencesKey.USER_CITY_KEY] = city
        }
    }

    override suspend fun saveWeatherData(
        weatherDataJSON: String,
        context: Context,
    ) {
        val ts = System.currentTimeMillis()
        context.dataStore.edit { preferences ->
            preferences[PreferencesKey.WEATHER_DATA_KEY] = weatherDataJSON
            preferences[PreferencesKey.TIME_MS_KEY] = ts
        }
    }
}