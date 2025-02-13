package com.example.weather.weather

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weather.const.Const.API_KEY_FW
import com.example.weather.data.WeatherInfo
import org.json.JSONObject

fun getData(city: String, context: Context, daysList: MutableState<List<WeatherInfo>>, day: MutableState<WeatherInfo>){
    val url = "https://api.weatherapi.com/v1/forecast.json?key=$API_KEY_FW&q=$city&days=2&aqi=no&alerts=no"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        com.android.volley.Request.Method.GET,
        url,
        {
            response ->
            val list = getWeatherInfoByDays(response)
            day.value = list[0]
            daysList.value = list
        },
        {
            error ->
            Log.e("MyLog", "error: $error")
        }
    )
    queue.add(stringRequest)
}