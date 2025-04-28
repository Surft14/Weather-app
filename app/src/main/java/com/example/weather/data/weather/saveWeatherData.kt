package com.example.weather.data.weather

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.weather.data.const.PreferencesKey
import com.example.weather.data.dataStore


suspend fun saveWeatherData(city: String, weatherDataJSON: String, context: Context){
    context.dataStore.edit { preferences ->
        preferences[PreferencesKey.WEATHER_DATA_KEY] = weatherDataJSON
        preferences[PreferencesKey.USER_CITY_KEY] = city
    }
}