package com.example.weather.data.weather

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.weather.data.const.PreferencesKey
import com.example.weather.data.dataStore


suspend fun saveWeatherData(weatherDataJSON: String, context: Context){
    val ts = System.currentTimeMillis()
    context.dataStore.edit { preferences ->
        preferences[PreferencesKey.WEATHER_DATA_KEY] = weatherDataJSON
        preferences[PreferencesKey.TIME_MS_KEY] = ts
    }
}
suspend fun saveCity(city: String, context: Context){
    context.dataStore.edit { pref ->
        pref[PreferencesKey.USER_CITY_KEY] = city
    }
}