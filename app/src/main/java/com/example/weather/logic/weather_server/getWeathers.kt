package com.example.weather.logic.weather_server

import android.util.Log
import com.example.weather.data.model.Weathers
import org.json.JSONObject

fun getWeathers(response: String): List<Weathers> {
    Log.d("MyLog", "getWeathers start")

    val listWeathers = ArrayList<Weathers>()
    val mainOdj = JSONObject(response)
    
    val weathers = mainOdj.getJSONArray("weathersList")
    
    for (i in 0 until weathers.length()){
        val weathersItem = weathers[i] as JSONObject
        
        listWeathers.add(
            Weathers(
                city = weathersItem.getString("city"),
                avgTemp = weathersItem.getString("avgTemp"),
                maxWind = weathersItem.getString("maxWind"),
                text = weathersItem.getString("text"),
                icon = weathersItem.getString("icon"),
                code = weathersItem.getInt("code")
            )
        )
        
    }

    return listWeathers
}