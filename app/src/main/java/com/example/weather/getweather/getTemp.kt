package com.example.weather.getweather

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weather.API_KEY
import com.example.weather.LANGUAGE
import com.example.weather.UNITS
import org.json.JSONObject

fun getTemp(city: String, state: MutableState<String>, context: Context){
    val url = "https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=$API_KEY&units=$UNITS&lang=$LANGUAGE"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        com.android.volley.Request.Method.GET,
        url,
        {
                response ->
            val obj  = JSONObject(response)
            try{
                state.value = obj.getJSONObject("main").getString("temp")
            }
            catch (e: Exception){
                Log.e("MyLog", "$e")
            }
        },
        {
                error ->
            Log.e("MyLog", "$error")
        }
    )
    queue.add(stringRequest)
}