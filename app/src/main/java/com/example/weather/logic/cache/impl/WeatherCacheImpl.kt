package com.example.weather.logic.cache.impl

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
import com.example.weather.logic.cache.interfaces.WeatherCache
import kotlinx.coroutines.flow.first
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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

    override suspend fun readWeatherData(context: Context) : String? {
        val preferences = context.dataStore.data.first()
        val weatherData = preferences[PreferencesKey.WEATHER_DATA_KEY]
        val ts = preferences[PreferencesKey.TIME_MS_KEY] ?: 0L
        return if (System.currentTimeMillis() - ts <= WEATHER_TTL_MS){
            weatherData
        } else{
            Log.i("MyLog", "clear data")
            clearStaleWeatherData(context)
            null
        }
    }

    override suspend fun readUserCity(context: Context): String? {
        val preferences = context.dataStore.data.first()
        val cityPref = preferences[PreferencesKey.USER_CITY_KEY]
        return if (cityPref != null){
            Log.d("MyLog", "readUserCity city: $cityPref")
            cityPref
        } else{
            Log.d("MyLog", "readUserCity city: $cityPref")
            null
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