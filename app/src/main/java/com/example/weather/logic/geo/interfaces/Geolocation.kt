package com.example.weather.logic.geo.interfaces

import android.content.Context
import com.example.weather.data.model.Coordinate

interface Geolocation {
    suspend fun getCoordinate(context: Context): Coordinate?

    suspend fun getCity(coordinate: Coordinate, context: Context): String?
}