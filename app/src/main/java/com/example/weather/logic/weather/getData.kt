package com.example.weather.logic.weather

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weather.data.const.Const.API_KEY_FW
import com.example.weather.data.WeatherInfo
import com.example.weather.data.weather.saveWeatherData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

fun getData(
    city: String,
    context: Context,
    daysList: MutableState<List<WeatherInfo>>,
    day: MutableState<WeatherInfo>,
) {
    val url =
        "https://api.weatherapi.com/v1/forecast.json?key=$API_KEY_FW&q=$city&days=7&aqi=no&alerts=no"
    val queue = Volley.newRequestQueue(context)
    val ioScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        { response ->
            val list = getWeatherInfoByDays(response)
            day.value = list[0]
            daysList.value = list
            ioScope.launch {
                saveWeatherData(city, response, context)
            }
        },
        { error ->
            Log.e("MyLog", "Get data error: $error")
        }
    ).apply {
        setShouldCache(false)
        retryPolicy = DefaultRetryPolicy(
            5_000, // таймаут: 5 секунд
            3,      // максимальное количество повторов
            1.0f,   // множитель увеличения задержки (1.0 = линейно)
        )
    }
    queue.add(stringRequest)
}