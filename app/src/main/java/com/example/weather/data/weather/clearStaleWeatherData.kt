package com.example.weather.data.weather

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.weather.data.const.Const.WEATHER_TTL_MS
import com.example.weather.data.const.PreferencesKey.TIME_MS_KEY
import com.example.weather.data.const.PreferencesKey.WEATHER_DATA_KEY
import com.example.weather.data.dataStore

suspend fun clearStaleWeatherData(context: Context) {
    context.dataStore.edit { prefs ->
        val ts = prefs[TIME_MS_KEY] ?: return@edit
        if (System.currentTimeMillis() - ts > WEATHER_TTL_MS) {
            prefs.remove(WEATHER_DATA_KEY)
            prefs.remove(TIME_MS_KEY)
        }
    }
}