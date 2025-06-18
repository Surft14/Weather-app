package com.example.weather.data.const

import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Const{
    const val WEATHER_TTL_MS = 60 * 60 * 1_000L // один час
}

object PreferencesKey{
    val WEATHER_DATA_KEY = stringPreferencesKey("weatherData")
    val USER_CITY_KEY = stringPreferencesKey("userCity")
    val IMAGE_BACK_ROUND = stringPreferencesKey("imageBackRound")
    val TIME_MS_KEY = longPreferencesKey("timeMsKey")
}

object WeatherCodes {
    const val SUNNY = 1000
    const val PARTLY_CLOUDY = 1003
    const val CLOUDY = 1006
    const val OVERCAST = 1009
    const val MIST = 1030
    const val PATCHY_RAIN_POSSIBLE = 1063
    const val PATCHY_SNOW_POSSIBLE = 1066
    const val PATCHY_SLEET_POSSIBLE = 1069
    const val PATCHY_FREEZING_DRIZZLE_POSSIBLE = 1072
    const val THUNDERY_OUTBREAKS_POSSIBLE = 1087
    const val BLOWING_SNOW = 1114
    const val BLIZZARD = 1117
    const val FOG = 1135
    const val FREEZING_FOG = 1147
    const val PATCHY_LIGHT_DRIZZLE = 1150
    const val LIGHT_DRIZZLE = 1153
    const val FREEZING_DRIZZLE = 1168
    const val HEAVY_FREEZING_DRIZZLE = 1171
    const val PATCHY_LIGHT_RAIN = 1180
    const val LIGHT_RAIN = 1183
    const val MODERATE_RAIN_AT_TIMES = 1186
    const val MODERATE_RAIN = 1189
    const val HEAVY_RAIN_AT_TIMES = 1192
    const val HEAVY_RAIN = 1195
    const val LIGHT_FREEZING_RAIN = 1198
    const val MODERATE_OR_HEAVY_FREEZING_RAIN = 1201
    const val LIGHT_SLEET = 1204
    const val MODERATE_OR_HEAVY_SLEET = 1207
    const val PATCHY_LIGHT_SNOW = 1210
    const val LIGHT_SNOW = 1213
    const val PATCHY_MODERATE_SNOW = 1216
    const val MODERATE_SNOW = 1219
    const val PATCHY_HEAVY_SNOW = 1222
    const val HEAVY_SNOW = 1225
    const val ICE_PELLETS = 1237
    const val LIGHT_RAIN_SHOWER = 1240
    const val MODERATE_OR_HEAVY_RAIN_SHOWER = 1243
    const val TORRENTIAL_RAIN_SHOWER = 1246
    const val LIGHT_SLEET_SHOWERS = 1249
    const val MODERATE_OR_HEAVY_SLEET_SHOWERS = 1252
    const val LIGHT_SNOW_SHOWERS = 1255
    const val MODERATE_OR_HEAVY_SNOW_SHOWERS = 1258
    const val LIGHT_SHOWERS_OF_ICE_PELLETS = 1261
    const val MODERATE_OR_HEAVY_SHOWERS_OF_ICE_PELLETS = 1264
    const val PATCHY_LIGHT_RAIN_WITH_THUNDER = 1273
    const val MODERATE_OR_HEAVY_RAIN_WITH_THUNDER = 1276
    const val PATCHY_LIGHT_SNOW_WITH_THUNDER = 1279
    const val MODERATE_OR_HEAVY_SNOW_WITH_THUNDER = 1282
}
//sunny - солнечно
//partly_cloudy - переменная облачность
//cloudy - облачно
//overcast - пасмурно
//mist - туман
//patchy_rain_possible - возможен кратковременный дождь
//patchy_snow_possible - возможен кратковременный снег
//patchy_sleet_possible - возможен кратковременный мокрый снег
//patchy_freezing_drizzle_possible - возможна кратковременная изморозь
//thundery_outbreaks_possible - возможны грозы
//blowing_snow - метель
//blizzard - буран
//fog - туман
//freezing_fog - ледяной туман
//patchy_light_drizzle - кратковременная слабая морось
//light_drizzle - слабая морось
//freezing_drizzle - ледяная морось
//heavy_freezing_drizzle - сильная ледяная морось
//patchy_light_rain - кратковременный слабый дождь
//light_rain - слабый дождь
//moderate_rain_at_times - умеренный дождь временами
//moderate_rain - умеренный дождь
//heavy_rain_at_times - сильный дождь временами
//heavy_rain - сильный дождь
//light_freezing_rain - слабый ледяной дождь
//moderate_or_heavy_freezing_rain - умеренный или сильный ледяной дождь
//light_sleet - слабый мокрый снег
//moderate_or_heavy_sleet - умеренный или сильный мокрый снег
//patchy_light_snow - кратковременный слабый снег
//light_snow - слабый снег
//patchy_moderate_snow - кратковременный умеренный снег
//moderate_snow - умеренный снег
//patchy_heavy_snow - кратковременный сильный снег
//heavy_snow - сильный снег
//ice_pellets - ледяные гранулы
//light_rain_shower - слабый ливень
//moderate_or_heavy_rain_shower - умеренный или сильный ливень
//torrential_rain_shower - проливной ливень
//light_sleet_showers - слабые ливни с мокрым снегом
//moderate_or_heavy_sleet_showers - умеренные или сильные ливни с мокрым снегом
//light_snow_showers - слабые ливни со снегом
//moderate_or_heavy_snow_showers - умеренные или сильные ливни со снегом
//light_showers_of_ice_pellets - слабые ливни с ледяными гранулами
//moderate_or_heavy_showers_of_ice_pellets - умеренные или сильные ливни с ледяными гранулами
//patchy_light_rain_with_thunder - кратковременный слабый дождь с грозой
//moderate_or_heavy_rain_with_thunder - умеренный или сильный дождь с грозой
//patchy_light_snow_with_thunder - кратковременный слабый снег с грозой
//moderate_or_heavy_snow_with_thunder - умеренный или сильный снег с грозой


