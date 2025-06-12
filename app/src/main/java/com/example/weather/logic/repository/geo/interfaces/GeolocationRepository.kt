package com.example.weather.logic.repository.geo.interfaces

import android.content.Context
import com.example.weather.data.model.Coordinate

interface GeolocationRepository {
    suspend fun getCoordinate(context: Context): Coordinate?
    suspend fun getCity(coordinate: Coordinate, context: Context): String?
    suspend fun getCity(context: Context): String?
}