package com.example.weather.logic.weather_server

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weather.data.model.WeatherHour
import com.example.weather.data.model.WeatherNow
import com.example.weather.data.model.Weathers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import com.android.volley.Request
import com.example.weather.data.weather.saveWeatherData
import kotlinx.coroutines.launch


fun getWeather(city: String,
               context: Context,
               weatherNow: MutableState<WeatherNow>,
               listWeatherHour: MutableState<List<WeatherHour>>,
               listWeathers: MutableState<List<Weathers>>
) {
    val url = "http:localhost:8085/api/v1/weathers_now/get/db/city/weather_now?city=$city"
    val queue = Volley.newRequestQueue(context)
    val ioScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        { response ->
            weatherNow.value = getWeatherNow(response)
            listWeatherHour.value = getWeatherHour(response)
            listWeathers.value = getWeathers(response)
            ioScope.launch {
                saveWeatherData(response, context)
            }
        },
        { err ->
            Log.e("MyLog", "Get data from weather-server error: ", err)
        }
    )
    queue.add(stringRequest)
}