package com.example.weather.logic.repository.geo.impl

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.example.weather.data.model.Coordinate
import com.example.weather.logic.geo.impl.GeolocationImpl
import com.example.weather.logic.geo.interfaces.Geolocation
import com.example.weather.logic.repository.geo.interfaces.GeolocationRepository

class GeolocationRepositoryImpl(
    private val geolocation: Geolocation = GeolocationImpl(),
) : GeolocationRepository {

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override suspend fun getCoordinate(context: Context): Coordinate? {
        return geolocation.getCoordinate(context)
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override suspend fun getCity(
        coordinate: Coordinate,
        context: Context,
    ): String? {
        return geolocation.getCity(coordinate, context)
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override suspend fun getCity(context: Context): String? {
        val coordinate = getCoordinate(context)
        if (coordinate != null) {
            return getCity(coordinate, context)
        }
        return null
    }
}