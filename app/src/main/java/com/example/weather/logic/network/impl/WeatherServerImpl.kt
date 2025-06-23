package com.example.weather.logic.network.impl

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weather.data.const.WeatherCodes
import com.example.weather.data.model.WeatherForecast
import com.example.weather.data.model.WeatherHour
import com.example.weather.data.model.WeatherInfo
import com.example.weather.data.model.WeatherNow
import com.example.weather.logic.network.CustomHurlStack
import com.example.weather.logic.network.interfaces.WeatherServer
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class WeatherServerImpl() : WeatherServer {

    override suspend fun fetchWeatherJSON(
        city: String,
        context: Context,
    ) : String? = suspendCoroutine { continuation ->
        Log.d("MyLog", "server fetchWeatherJSON start")
        val requestQueue = Volley.newRequestQueue(context, CustomHurlStack(context))
        //192.168.0.122
        //85.234.7.243
        val url = "https://85.234.7.243:8443/api/v1/weathers_now/get/db/city/weather_now?city=$city"
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                Log.i("MyLog", "server fetchWeatherJSON info: $response")
                continuation.resume(response)
            },
            { err ->
                Log.e("MyLog", "Get data from weather-server error: ", err)
                continuation.resume(null)
            }
        )

        val policy = DefaultRetryPolicy(
            15000, // 10 секунд таймаут
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        stringRequest.setRetryPolicy(policy)
        requestQueue.add(stringRequest)
        Log.i("MyLog", "server fetchWeatherJSON info: $stringRequest")
    }

    override suspend fun parseWeatherHour(json: String): List<WeatherHour> {
        Log.d("MyLog", "server parseWeatherHour start")
        if (json.isEmpty()) return listOf()

        val listWeatherHour : ArrayList<WeatherHour> = ArrayList<WeatherHour>()
        val mainObj = JSONObject(json)
        val listHour = mainObj.getJSONArray("listHour")

        for(i in 0 until listHour.length() ){
            val hour = listHour[i] as JSONObject

            listWeatherHour.add(
                WeatherHour(
                    city = hour.getString("city"),

                    time = hour.getString("time"),
                    date = hour.getString("date"),

                    temp = hour.getString("temp"),
                    tempF = hour.getString("tempF"),
                    feelLike = hour.getString("feelLike"),
                    feelLikeF = hour.getString("feelLikeF"),

                    icon = hour.getString("icon"),
                    text = hour.getString("text"),
                    code = hour.getInt("code"),

                    dir = hour.getString("dir"),
                    speed = hour.getString("speed"),
                    speedM = hour.getString("speedM")
                )
            )

        }
        Log.i("MyLog", "server parseWeatherHour info: ${listWeatherHour[1]}")
        return listWeatherHour
    }

    override suspend fun parseWeatherNow(json: String): WeatherNow {
        Log.d("MyLog", "server parseWeatherNow Start")
        if (json.isEmpty()) return WeatherNow(
            city = "",
            region = "",
            country = "",
            dateTime = "",
            lastDateTime = "",
            temp = "",
            feelLike = "",
            speed = "",
            dir = "",
            icon = "",
            text = "",
            code = 0
        )
        val weatherNow: WeatherNow = WeatherNow(
            city = "",
            region = "",
            country = "",
            dateTime = "",
            lastDateTime = "",
            temp = "",
            feelLike = "",
            speed = "",
            dir = "",
            icon = "",
            text = "",
            code = 0
        )

        try{
            val mainObj = JSONObject(json)

            weatherNow.city = mainObj.getString("city")
            weatherNow.region = mainObj.getString("region")
            weatherNow.country = mainObj.getString("country")

            val rawDate = mainObj.getString("dateTime")
            val formattedDate = rawDate.replace("T", " ")
            weatherNow.dateTime = formattedDate
            weatherNow.date = mainObj.getString("date")
            weatherNow.time = mainObj.getString("time")
            weatherNow.lastDateTime = mainObj.getString("lastUpdateTime")

            weatherNow.temp = mainObj.getString("temp")
            weatherNow.tempF = mainObj.getString("tempF")
            weatherNow.feelLike = mainObj.getString("feelLike")
            weatherNow.feelLikeF = mainObj.getString("feelLikeF")
            weatherNow.dir = mainObj.getString("dir")
            weatherNow.speed = mainObj.getString("speed")
            weatherNow.speedM = mainObj.getString("speedM")

            weatherNow.text = mainObj.getString("text")
            weatherNow.icon = mainObj.getString("icon")
            weatherNow.code = mainObj.getInt("code")
        }catch (e : Exception){
            Log.e("MyLog", "server parseWeatherNow Error: $e")
        }
        Log.i("MyLog", "server parseWeatherNow info: $weatherNow")
        return weatherNow
    }

    override suspend fun parseWeatherForecast(json: String): List<WeatherForecast> {
        if (json.isEmpty()) return listOf()
        Log.d("MyLog", "server parseWeatherForecast start")

        val listWeathers = ArrayList<WeatherForecast>()
        val mainOdj = JSONObject(json)

        val weathers = mainOdj.getJSONArray("weathersList")

        for (i in 0 until weathers.length()){
            val weathersItem = weathers[i] as JSONObject

            listWeathers.add(
                WeatherForecast(
                    city = weathersItem.getString("city"),
                    date = weathersItem.getString("date"),
                    avgTemp = weathersItem.getString("avgTemp"),
                    avgTempF = weathersItem.getString("avgTempF"),
                    maxWind = weathersItem.getString("maxWind"),
                    maxWindM = weathersItem.getString("maxWindM"),
                    text = weathersItem.getString("text"),
                    icon = weathersItem.getString("icon"),
                    code = weathersItem.getInt("code")
                )
            )

        }
        Log.i("MyLog", "server parseWeatherForecast info: ${listWeathers[1]}")
        return listWeathers
    }

    override suspend fun getWeatherCondition(day: WeatherInfo?): String {
        Log.d("MyLog", "server getWeatherCondition start")
        Log.i("MyLog", "server getWeatherCondition info: $day")

        return when (day?.weatherNow?.code) {
            WeatherCodes.SUNNY -> {
                // Солнечно
                "sunny"
            }

            WeatherCodes.PARTLY_CLOUDY -> {
                // Переменная облачность
                "partly_cloudy"
            }

            WeatherCodes.CLOUDY -> {
                // Облачно
                "cloudy"
            }

            WeatherCodes.OVERCAST -> {
                // Пасмурно
                "overcast"
            }

            WeatherCodes.MIST -> {
                // Туман
                "mist"
            }

            WeatherCodes.PATCHY_RAIN_POSSIBLE -> {
                // Возможен кратковременный дождь
                "patchy_rain_possible"
            }

            WeatherCodes.PATCHY_SNOW_POSSIBLE -> {
                // Возможен кратковременный снег
                "patchy_snow_possible"
            }

            WeatherCodes.PATCHY_SLEET_POSSIBLE -> {
                // Возможен кратковременный мокрый снег
                "patchy_sleet_possible"
            }

            WeatherCodes.PATCHY_FREEZING_DRIZZLE_POSSIBLE -> {
                // Возможна кратковременная изморозь
                "patchy_freezing_drizzle_possible"
            }

            WeatherCodes.THUNDERY_OUTBREAKS_POSSIBLE -> {
                // Возможны грозы
                "thundery_outbreaks_possible"
            }

            WeatherCodes.BLOWING_SNOW -> {
                // Метель
                "blowing_snow"
            }

            WeatherCodes.BLIZZARD -> {
                // Буран
                "blizzard"
            }

            WeatherCodes.FOG -> {
                // Туман
                "fog"
            }

            WeatherCodes.FREEZING_FOG -> {
                // Ледяной туман
                "freezing_fog"
            }

            WeatherCodes.PATCHY_LIGHT_DRIZZLE -> {
                // Кратковременная слабая морось
                "patchy_light_drizzle"
            }

            WeatherCodes.LIGHT_DRIZZLE -> {
                // Слабая морось
                "light_drizzle"
            }

            WeatherCodes.FREEZING_DRIZZLE -> {
                // Ледяная морось
                "freezing_drizzle2"
            }

            WeatherCodes.HEAVY_FREEZING_DRIZZLE -> {
                // Сильная ледяная морось
                "heavy_freezing_drizzle"
            }

            WeatherCodes.PATCHY_LIGHT_RAIN -> {
                // Кратковременный слабый дождь
                "patchy_light_rain"
            }

            WeatherCodes.LIGHT_RAIN -> {
                // Слабый дождь
                "light_rain"
            }

            WeatherCodes.MODERATE_RAIN_AT_TIMES -> {
                // Умеренный дождь временами
                "moderate_rain_at_times"
            }

            WeatherCodes.MODERATE_RAIN -> {
                // Умеренный дождь
                "moderate_rain"
            }

            WeatherCodes.HEAVY_RAIN_AT_TIMES -> {
                // Сильный дождь временами
                "heavy_rain"
            }

            WeatherCodes.HEAVY_RAIN -> {
                // Сильный дождь
                "heavy_rain"
            }

            WeatherCodes.LIGHT_FREEZING_RAIN -> {
                // Слабый ледяной дождь
                "light_freezing_rain"
            }

            WeatherCodes.MODERATE_OR_HEAVY_FREEZING_RAIN -> {
                // Умеренный или сильный ледяной дождь
                "moderate_or_heavy_freezing_rain"
            }

            WeatherCodes.LIGHT_SLEET -> {
                // Слабый мокрый снег
                "light_sleet"
            }

            WeatherCodes.MODERATE_OR_HEAVY_SLEET -> {
                // Умеренный или сильный мокрый снег
                "moderate_or_heavy_sleet"
            }

            WeatherCodes.PATCHY_LIGHT_SNOW -> {
                // Кратковременный слабый снег
                "light_snow"
            }

            WeatherCodes.LIGHT_SNOW -> {
                // Слабый снег
                "light_snow"
            }

            WeatherCodes.PATCHY_MODERATE_SNOW -> {
                // Кратковременный умеренный снег
                "patchy_moderate_snow"
            }

            WeatherCodes.MODERATE_SNOW -> {
                // Умеренный снег
                "moderate_snow"
            }

            WeatherCodes.PATCHY_HEAVY_SNOW -> {
                // Кратковременный сильный снег
                "heavy_snow"
            }

            WeatherCodes.HEAVY_SNOW -> {
                // Сильный снег
                "heavy_snow"
            }

            WeatherCodes.ICE_PELLETS -> {
                // Ледяные гранулы
                "ice_pellets"
            }

            WeatherCodes.LIGHT_RAIN_SHOWER -> {
                // Слабый ливень
                "light_rain_shower"
            }

            WeatherCodes.MODERATE_OR_HEAVY_RAIN_SHOWER -> {
                // Умеренный или сильный ливень
                "heavy_rain_shower"
            }

            WeatherCodes.TORRENTIAL_RAIN_SHOWER -> {
                // Проливной ливень
                "heavy_rain"
            }

            WeatherCodes.LIGHT_SLEET_SHOWERS -> {
                // Слабые ливни с мокрым снегом
                "light_sleet_showers"
            }

            WeatherCodes.MODERATE_OR_HEAVY_SLEET_SHOWERS -> {
                // Умеренные или сильные ливни с мокрым снегом
                "light_sleet_showers"
            }

            WeatherCodes.LIGHT_SNOW_SHOWERS -> {
                // Слабые ливни со снегом
                "light_snow"
            }

            WeatherCodes.MODERATE_OR_HEAVY_SNOW_SHOWERS -> {
                // Умеренные или сильные ливни со снегом
                "heavy_snow"
            }

            WeatherCodes.LIGHT_SHOWERS_OF_ICE_PELLETS -> {
                // Слабые ливни с ледяными гранулами
                "ice_pellets"
            }

            WeatherCodes.MODERATE_OR_HEAVY_SHOWERS_OF_ICE_PELLETS -> {
                // Умеренные или сильные ливни с ледяными гранулами
                "ice_pellets"
            }

            WeatherCodes.PATCHY_LIGHT_RAIN_WITH_THUNDER -> {
                // Кратковременный слабый дождь с грозой
                "rain_with_thunde"
            }

            WeatherCodes.MODERATE_OR_HEAVY_RAIN_WITH_THUNDER -> {
                // Умеренный или сильный дождь с грозой
                "rain_with_thunde"
            }

            WeatherCodes.PATCHY_LIGHT_SNOW_WITH_THUNDER -> {
                // Кратковременный слабый снег с грозой
                "patchy_light_snow_with_thunder"
            }

            WeatherCodes.MODERATE_OR_HEAVY_SNOW_WITH_THUNDER -> {
                // Умеренный или сильный снег с грозой
                "patchy_light_snow_with_thunder"
            }

            else -> {
                // Неизвестное погодное условие
                "skybox"
            }
        }
    }
}