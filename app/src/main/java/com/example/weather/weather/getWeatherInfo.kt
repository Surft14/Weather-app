package com.example.weather.weather

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weather.const.Const.API_KEY_FW
import com.example.weather.const.Const.LANGUAGE
import com.example.weather.const.Const.UNITS
import com.example.weather.data.WeatherInfo
import org.json.JSONObject

fun getWeatherInfo(city: String, state: MutableState<WeatherInfo>, context: Context){
    val url = "https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=$API_KEY_FW&units=$UNITS&lang=$LANGUAGE"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        com.android.volley.Request.Method.GET,
        url,
        {
            response ->
            val obj  = JSONObject(response)
            try{
                state.value.temp = obj.getJSONObject("main").getString("temp")
                state.value.weather = obj.getJSONArray("weather").getJSONObject(0).getString("main")
                state.value.feelLike = obj.getJSONObject("main").getString("feels_like")
                state.value.lat = obj.getJSONObject("coord").getDouble("lat")
                state.value.lon = obj.getJSONObject("coord").getDouble("lon")
                Log.d("MyLog", "getWeatherInfo start")
                Log.i("MyLog", "Temp now: ${state.value.temp}")
                Log.i("MyLog", "Feel like now: ${state.value.feelLike}")
                Log.i("MyLog", "Weather now: ${state.value.weather}")
                Log.i("MyLog", "Lat now: ${state.value.lat}")
                Log.i("MyLog", "Lot now: ${state.value.lon}")


            }
            catch (e: Exception){
                Log.e("MyLog", "Error: ${e.message}")
            }
        },
        {
            error ->
            Log.e("MyLog", "getWeatherInfo error: $error")
        }
    )
    queue.add(stringRequest)
}