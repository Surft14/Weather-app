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

fun getMinMaxTemp(city: String, state: MutableState<WeatherInfo>, context: Context){
    val oneCallUrl = "https://api.openweathermap.org/data/2.5/forecast?q=$city&appid=$API_KEY_FW&units=$UNITS&lang=$LANGUAGE"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        com.android.volley.Request.Method.GET,
        oneCallUrl,
        {
            response ->
            try {
                val jsonObject = JSONObject(response)
                val listArray = jsonObject.getJSONArray("list")
                var minTemp = Double.MAX_VALUE
                var maxTemp = Double.MIN_VALUE

                for (i in 0 until listArray.length()) {
                    val item = listArray.getJSONObject(i)
                    val mainObj = item.getJSONObject("main")

                    val tempMin = mainObj.getDouble("temp_min")
                    val tempMax = mainObj.getDouble("temp_max")

                    if (tempMin < minTemp) minTemp = tempMin
                    if (tempMax > maxTemp) maxTemp = tempMax
                }

                Log.d("MyLog", "getMinMaxTemp start")

                state.value.tempMax = normalizeTemperature(minTemp)
                state.value.tempMin = normalizeTemperature(maxTemp)
                Log.i("MyLog", "Temp max now: ${state.value.tempMax}")
                Log.i("MyLog", "Temp min now: ${state.value.tempMin}")
            } catch (e: Exception) {
                Log.e("MyLog", "Error parsing forecastResponse: ${e.message}")
            }
        },
        {
            error ->
            Log.e("MyLog", "Error getMinMaxTemp: $error")
        }
    )
    queue.add(stringRequest)
}

