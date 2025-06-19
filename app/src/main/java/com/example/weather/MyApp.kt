package com.example.weather

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.datastore.preferences.core.edit
import com.example.weather.data.const.Const
import com.example.weather.data.const.PreferencesKey
import com.example.weather.data.dataStore
import com.example.weather.logic.cache.CachedWeather
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

        if (currentTime - ts <= Const.WEATHER_TTL_MS){
            CachedWeather.weatherJSON = preferences[PreferencesKey.WEATHER_DATA_KEY]
            CachedWeather.imageBase64 = preferences[PreferencesKey.IMAGE_BACKGROUND_KEY]
        }
        if(CachedWeather.imageBase64.isNullOrEmpty()){
            val stream = ByteArrayOutputStream()
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.skybox)
            bitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()
            val imageDefault = Base64.encodeToString(byteArray, Base64.DEFAULT)
            dataStore.edit { preferences ->
                preferences[PreferencesKey.IMAGE_BACKGROUND_KEY] = imageDefault
            }
            CachedWeather.imageBase64 = imageDefault
        }
    }
}