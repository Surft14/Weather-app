package com.example.weather.utils

import android.util.Log
import androidx.compose.runtime.Composable
import com.example.weather.R
import com.example.weather.data.model.WeatherInfo
import com.example.weather.data.const.WeatherCodes
@Composable
fun getWeatherCondition(day: WeatherInfo?): String {

    Log.d("MyLog", "getWeatherCondition start")
    Log.i("MyLog", "getWeatherCondition info: $day")

    return when (day?.weatherNow?.code) {
        WeatherCodes.SUNNY -> {
            // Солнечно
            "skybox"
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
            "skybox"
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
            "skybox"
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
