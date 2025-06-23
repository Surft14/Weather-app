package com.example.weather

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.datastore.preferences.core.edit
import com.example.weather.data.const.Const
import com.example.weather.data.const.Const.WEATHER_TTL_MS
import com.example.weather.data.const.PreferencesKey
import com.example.weather.data.dataStore
import com.example.weather.logic.cache.CachedWeather
import com.example.weather.utils.sha256
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.io.ByteArrayOutputStream

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        runBlocking {
            preloadCachedData()
        }
    }

    private suspend fun preloadCachedData() {
        val context = applicationContext
        val dataStore = context.dataStore
        val preferences = dataStore.data.first()
        val currentTime = System.currentTimeMillis()

        CachedWeather.cityName = preferences[PreferencesKey.USER_CITY_KEY]
        val ts = preferences[PreferencesKey.TIME_MS_KEY] ?: 0L
        dataStore.edit { prefs ->
            if (currentTime - ts > WEATHER_TTL_MS){
                prefs.remove(PreferencesKey.WEATHER_DATA_KEY)
                prefs.remove(PreferencesKey.IMAGE_BACKGROUND_KEY)
                prefs.remove(PreferencesKey.TIME_MS_KEY)
            }
        }
        CachedWeather.imageBase64 = preferences[PreferencesKey.IMAGE_BACKGROUND_KEY]

        val stream = ByteArrayOutputStream()
        val skyboxBitmap = BitmapFactory.decodeResource(resources, R.drawable.skybox)
        skyboxBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val skyboxBytes = stream.toByteArray()
        val skyboxBase64 = Base64.encodeToString(skyboxBytes, Base64.DEFAULT)
        CachedWeather.skyboxHash = skyboxBase64.sha256()

        if (CachedWeather.imageBase64.isNullOrEmpty()) {
            CachedWeather.imageBase64 = skyboxBase64
        }

        if (currentTime - ts <= Const.WEATHER_TTL_MS) {
            CachedWeather.weatherJSON = preferences[PreferencesKey.WEATHER_DATA_KEY]
        }
    }

}