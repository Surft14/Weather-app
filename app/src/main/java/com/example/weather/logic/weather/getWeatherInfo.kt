package com.example.weather.logic.weather

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weather.const.Const.API_KEY_FW
import com.example.weather.const.Const.LANGUAGE
import com.example.weather.const.Const.UNITS
import com.example.weather.data.WeatherInfo
import org.json.JSONObject

fun getWeatherInfo(city: String, state: MutableState<WeatherInfo>, context: Context){
    val url = "https://api.weatherapi.com/v1/forecast.json?key=$API_KEY_FW&q=$city&days=3&aqi=no&alerts=no"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        {
            response ->
            val obj  = JSONObject(response)
            try{
                Log.d("MyLog", "getWeatherInfo start")
                state.value.city = city
                state.value.time = obj.getJSONObject("current").getString("last_updated")
                state.value.temp = obj.getJSONObject("current").getString("temp_c")
                state.value.weather = obj.getJSONObject("current").getJSONObject("condition").getString("text")
                state.value.feelLike = obj.getJSONObject("current").getString("feelslike_c").toFloat().toInt().toString()
                state.value.icon = obj.getJSONObject("current").getJSONObject("condition").getString("icon")
                state.value.tempMax = obj.getJSONObject("forecast")
                    .getJSONArray("forecastday")
                    .getJSONObject(2)
                    .getJSONObject("day")
                    .getDouble("maxtemp_c").toFloat().toInt().toString()
                state.value.tempMax = obj.getJSONObject("forecast")
                    .getJSONArray("forecastday")
                    .getJSONObject(2)
                    .getJSONObject("day")
                    .getDouble("mintemp_c").toFloat().toInt().toString()
                state.value.wind = obj.getDouble("wind_kph").toFloat().toInt().toString()
                state.value.windDir = obj.getString("wind_dir")

                Log.i("MyLog", "Icon now: ${state.value.icon}")
                Log.i("MyLog", "Temp now: ${state.value.temp}")
                Log.i("MyLog", "Feel like now: ${state.value.feelLike}")
                Log.i("MyLog", "Weather now: ${state.value.weather}")
                Log.i("MyLog", "Wind now: ${state.value.wind}")
                Log.i("MyLog", "Max: ${state.value.tempMax}")
                Log.i("MyLog", "Min: ${state.value.tempMin}")
                Log.i("MyLog", "Wind dir now: ${state.value.windDir}")
            }
            catch (e: Exception){
                Log.e("MyLog", "getWeatherInfo Error: ${e.message}")
            }
        },
        {
            error ->
            Log.e("MyLog", "getWeatherInfo error: $error")
        }
    )
    queue.add(stringRequest)
}