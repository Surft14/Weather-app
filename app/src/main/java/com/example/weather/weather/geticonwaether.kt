package com.example.weather.weather

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weather.const.Const
import org.json.JSONObject


fun getIconWeather(city: String, state: MutableState<String>, context: Context){
    val url = "https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=${Const.API_KEY_FW}&units=${Const.UNITS}&lang=${Const.LANGUAGE}"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        com.android.volley.Request.Method.GET,
        url,
        {
                response ->
            val obj  = JSONObject(response)
            try{
                state.value = obj.getJSONArray("weather").getJSONObject(0).getString("icon")
                Log.i("MyLog", "Weather now: ${state.value}")
            }
            catch (e: Exception){
                Log.e("MyLog", "Error: ${e.message}")
            }
        },
        {
                error ->
            Log.e("MyLog", "$error")
        }
    )
    queue.add(stringRequest)
}
