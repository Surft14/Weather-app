package com.example.weather.logic.cache.impl

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import com.example.weather.data.const.Const.WEATHER_TTL_MS
import com.example.weather.data.const.PreferencesKey
import com.example.weather.data.const.PreferencesKey.TIME_MS_KEY
import com.example.weather.data.const.PreferencesKey.WEATHER_DATA_KEY
import com.example.weather.data.dataStore
import com.example.weather.logic.cache.interfaces.WeatherCache
import kotlinx.coroutines.flow.first

class WeatherCacheImpl : WeatherCache {
    override suspend fun clearStaleWeatherData(context: Context) {
        Log.d("MyLog", "Cache clearStaleWeatherData start")
        context.dataStore.edit { prefs ->
            val ts = prefs[TIME_MS_KEY] ?: return@edit
            if (System.currentTimeMillis() - ts > WEATHER_TTL_MS) {
                prefs.remove(WEATHER_DATA_KEY)
                prefs.remove(TIME_MS_KEY)
                prefs.remove(PreferencesKey.IMAGE_BACKGROUND_KEY)
            }
        }
    }

    override suspend fun readWeatherData(context: Context): String? {
        Log.d("MyLog", "Cache readWeatherData start")
        val preferences = context.dataStore.data.first()
        val weatherData = preferences[PreferencesKey.WEATHER_DATA_KEY]
        val ts = preferences[PreferencesKey.TIME_MS_KEY] ?: 0L
        return if (System.currentTimeMillis() - ts <= WEATHER_TTL_MS) {
            weatherData
        } else {
            Log.i("MyLog", "clear data")
            clearStaleWeatherData(context)
            null
        }
    }

    override suspend fun readUserCity(context: Context): String? {
        Log.d("MyLog", "Cache readUserCity start")
        val preferences = context.dataStore.data.first()
        val cityPref = preferences[PreferencesKey.USER_CITY_KEY]
        return if (cityPref != null) {
            Log.d("MyLog", "readUserCity city: $cityPref")
            cityPref
        } else {
            Log.d("MyLog", "readUserCity city: $cityPref")
            null
        }
    }

    override suspend fun readBase64(context: Context): String? {
        Log.d("MyLog", "Cache readBase64 start")
        val preferences = context.dataStore.data.first()
        val imageBase64 = preferences[PreferencesKey.IMAGE_BACKGROUND_KEY]
        val ts = preferences[PreferencesKey.TIME_MS_KEY] ?: 0L
        return if (System.currentTimeMillis() - ts <= WEATHER_TTL_MS) {
            imageBase64
        } else {
            Log.i("MyLog", "clear data")
            clearStaleWeatherData(context)
            null
        }
    }

    override suspend fun readIsFahrenheit(context: Context): Boolean? {
        Log.d("MyLog", "Cache readIsFahrenheit start")
        val preferences = context.dataStore.data.first()
        val isFahrenheit = preferences[PreferencesKey.IS_FAHRENHEIT]
        return isFahrenheit
    }

    override suspend fun readIsMile(context: Context): Boolean? {
        Log.d("MyLog", "Cache readIsMile start")
        val preferences = context.dataStore.data.first()
        val isMile = preferences[PreferencesKey.IS_MILE]
        return isMile
    }

    override suspend fun saveCity(city: String, context: Context) {
        Log.d("MyLog", "Cache saveCity start, ${city}")
        context.dataStore.edit { pref ->
            pref[PreferencesKey.USER_CITY_KEY] = city
        }
    }

    override suspend fun saveWeatherData(
        weatherDataJSON: String,
        context: Context,
    ) {
        Log.d("MyLog", "Cache saveWeatherData start, ${weatherDataJSON}")
        val ts = System.currentTimeMillis()
        context.dataStore.edit { preferences ->
            preferences[PreferencesKey.WEATHER_DATA_KEY] = weatherDataJSON
            preferences[PreferencesKey.TIME_MS_KEY] = ts
        }
    }

    override suspend fun saveBase64(image: String, context: Context) {
        Log.d("MyLog", "Cache saveBase64 start")
        val ts = System.currentTimeMillis()
        context.dataStore.edit { preferences ->
            preferences[PreferencesKey.IMAGE_BACKGROUND_KEY] = image
            preferences[PreferencesKey.TIME_MS_KEY] = ts
        }

    }

    override suspend fun saveIsMile(isMile: Boolean, context: Context) {
        Log.d("MyLog", "Cache saveIsMile start")
        context.dataStore.edit { preferences ->
            preferences[PreferencesKey.IS_MILE] = isMile
        }
    }

    override suspend fun saveIsFahrenheit(
        isFahrenheit: Boolean,
        context: Context,
    ) {
        Log.d("MyLog", "Cache saveIsFahrenheit start")
        context.dataStore.edit { preferences ->
            preferences[PreferencesKey.IS_FAHRENHEIT] = isFahrenheit
        }
    }
}