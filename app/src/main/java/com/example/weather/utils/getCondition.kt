package com.example.weather.utils

import com.example.weather.R
import com.example.weather.data.model.WeatherInfo
import com.example.weather.data.const.WeatherCodes

fun getWeatherCondition(day: WeatherInfo?): Int {

    return when (day?.weatherNow?.code) {
        WeatherCodes.SUNNY -> {
            // Солнечно
            R.drawable.skybox
        }

        WeatherCodes.PARTLY_CLOUDY -> {
            // Переменная облачность
            R.drawable.partly_cloudy
        }

        WeatherCodes.CLOUDY -> {
            // Облачно
            R.drawable.cloudy
        }

        WeatherCodes.OVERCAST -> {
            // Пасмурно
            R.drawable.overcast
        }

        WeatherCodes.MIST -> {
            // Туман
            R.drawable.mist
        }

        WeatherCodes.PATCHY_RAIN_POSSIBLE -> {
            // Возможен кратковременный дождь
            R.drawable.patchy_rain_possible
        }

        WeatherCodes.PATCHY_SNOW_POSSIBLE -> {
            // Возможен кратковременный снег
            R.drawable.patchy_snow_possible
        }

        WeatherCodes.PATCHY_SLEET_POSSIBLE -> {
            // Возможен кратковременный мокрый снег
            R.drawable.patchy_sleet_possible
        }

        WeatherCodes.PATCHY_FREEZING_DRIZZLE_POSSIBLE -> {
            // Возможна кратковременная изморозь
            R.drawable.patchy_freezing_drizzle_possible
        }

        WeatherCodes.THUNDERY_OUTBREAKS_POSSIBLE -> {
            // Возможны грозы
            R.drawable.thundery_outbreaks_possible
        }

        WeatherCodes.BLOWING_SNOW -> {
            // Метель
            R.drawable.blowing_snow
        }

        WeatherCodes.BLIZZARD -> {
            // Буран
            R.drawable.blizzard
        }

        WeatherCodes.FOG -> {
            // Туман
            R.drawable.fog
        }

        WeatherCodes.FREEZING_FOG -> {
            // Ледяной туман
            R.drawable.freezing_fog
        }

        WeatherCodes.PATCHY_LIGHT_DRIZZLE -> {
            // Кратковременная слабая морось
            R.drawable.patchy_light_drizzle
        }

        WeatherCodes.LIGHT_DRIZZLE -> {
            // Слабая морось
            R.drawable.light_drizzle
        }

        WeatherCodes.FREEZING_DRIZZLE -> {
            // Ледяная морось
            R.drawable.freezing_drizzle2
        }

        WeatherCodes.HEAVY_FREEZING_DRIZZLE -> {
            // Сильная ледяная морось
            R.drawable.heavy_freezing_drizzle
        }

        WeatherCodes.PATCHY_LIGHT_RAIN -> {
            // Кратковременный слабый дождь
            R.drawable.patchy_light_rain
        }

        WeatherCodes.LIGHT_RAIN -> {
            // Слабый дождь
            R.drawable.light_rain
        }

        WeatherCodes.MODERATE_RAIN_AT_TIMES -> {
            // Умеренный дождь временами
            R.drawable.moderate_rain_at_times
        }

        WeatherCodes.MODERATE_RAIN -> {
            // Умеренный дождь
            R.drawable.moderate_rain
        }

        WeatherCodes.HEAVY_RAIN_AT_TIMES -> {
            // Сильный дождь временами
            R.drawable.heavy_rain
        }

        WeatherCodes.HEAVY_RAIN -> {
            // Сильный дождь
            R.drawable.heavy_rain
        }

        WeatherCodes.LIGHT_FREEZING_RAIN -> {
            // Слабый ледяной дождь
            R.drawable.skybox
        }

        WeatherCodes.MODERATE_OR_HEAVY_FREEZING_RAIN -> {
            // Умеренный или сильный ледяной дождь
            R.drawable.moderate_or_heavy_freezing_rain
        }

        WeatherCodes.LIGHT_SLEET -> {
            // Слабый мокрый снег
            R.drawable.light_sleet
        }

        WeatherCodes.MODERATE_OR_HEAVY_SLEET -> {
            // Умеренный или сильный мокрый снег
            R.drawable.moderate_or_heavy_sleet
        }

        WeatherCodes.PATCHY_LIGHT_SNOW -> {
            // Кратковременный слабый снег
            R.drawable.light_snow
        }

        WeatherCodes.LIGHT_SNOW -> {
            // Слабый снег
            R.drawable.light_snow
        }

        WeatherCodes.PATCHY_MODERATE_SNOW -> {
            // Кратковременный умеренный снег
            R.drawable.skybox
        }

        WeatherCodes.MODERATE_SNOW -> {
            // Умеренный снег
            R.drawable.moderate_snow
        }

        WeatherCodes.PATCHY_HEAVY_SNOW -> {
            // Кратковременный сильный снег
            R.drawable.heavy_snow
        }

        WeatherCodes.HEAVY_SNOW -> {
            // Сильный снег
            R.drawable.heavy_snow
        }

        WeatherCodes.ICE_PELLETS -> {
            // Ледяные гранулы
            R.drawable.ice_pellets
        }

        WeatherCodes.LIGHT_RAIN_SHOWER -> {
            // Слабый ливень
            R.drawable.light_rain_shower
        }

        WeatherCodes.MODERATE_OR_HEAVY_RAIN_SHOWER -> {
            // Умеренный или сильный ливень
            R.drawable.heavy_rain_shower
        }

        WeatherCodes.TORRENTIAL_RAIN_SHOWER -> {
            // Проливной ливень
            R.drawable.heavy_rain
        }

        WeatherCodes.LIGHT_SLEET_SHOWERS -> {
            // Слабые ливни с мокрым снегом
            R.drawable.light_sleet_showers
        }

        WeatherCodes.MODERATE_OR_HEAVY_SLEET_SHOWERS -> {
            // Умеренные или сильные ливни с мокрым снегом
            R.drawable.light_sleet_showers
        }

        WeatherCodes.LIGHT_SNOW_SHOWERS -> {
            // Слабые ливни со снегом
            R.drawable.light_snow
        }

        WeatherCodes.MODERATE_OR_HEAVY_SNOW_SHOWERS -> {
            // Умеренные или сильные ливни со снегом
            R.drawable.heavy_snow
        }

        WeatherCodes.LIGHT_SHOWERS_OF_ICE_PELLETS -> {
            // Слабые ливни с ледяными гранулами
            R.drawable.ice_pellets
        }

        WeatherCodes.MODERATE_OR_HEAVY_SHOWERS_OF_ICE_PELLETS -> {
            // Умеренные или сильные ливни с ледяными гранулами
            R.drawable.ice_pellets
        }

        WeatherCodes.PATCHY_LIGHT_RAIN_WITH_THUNDER -> {
            // Кратковременный слабый дождь с грозой
            R.drawable.rain_with_thunde
        }

        WeatherCodes.MODERATE_OR_HEAVY_RAIN_WITH_THUNDER -> {
            // Умеренный или сильный дождь с грозой
            R.drawable.rain_with_thunde
        }

        WeatherCodes.PATCHY_LIGHT_SNOW_WITH_THUNDER -> {
            // Кратковременный слабый снег с грозой
            R.drawable.patchy_light_snow_with_thunder
        }

        WeatherCodes.MODERATE_OR_HEAVY_SNOW_WITH_THUNDER -> {
            // Умеренный или сильный снег с грозой
            R.drawable.patchy_light_snow_with_thunder
        }
        else -> {
            // Неизвестное погодное условие
            R.drawable.skybox
        }
    }
}
