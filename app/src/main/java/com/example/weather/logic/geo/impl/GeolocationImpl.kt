package com.example.weather.logic.geo.impl

import android.Manifest
import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.annotation.RequiresPermission
import com.example.weather.data.model.Coordinate
import com.example.weather.logic.cache.impl.WeatherCacheImpl
import com.example.weather.logic.cache.interfaces.WeatherCache
import com.example.weather.logic.geo.interfaces.Geolocation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class GeolocationImpl : Geolocation {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val weatherCache: WeatherCache = WeatherCacheImpl()

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override suspend fun getCoordinate(context: Context): Coordinate? =
        suspendCancellableCoroutine { cont ->
            Log.d("MyLog", "getCoordinate")
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        Log.d(
                            "MyLog",
                            "getCoordinate Success: ${location.latitude} : ${location.longitude}"
                        )
                        cont.resume(
                            Coordinate(
                                latitude = location.latitude,
                                longitude = location.longitude
                            )
                        )
                    } else {
                        Log.e("MyLog", "getCoordinate failed")
                    }
                }
                .addOnFailureListener {
                    cont.resume(null)
                }
        }


    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override suspend fun getCity(coordinate: Coordinate, context: Context): String? {
        Log.d("MyLog", "getCityFromCoordinate")
        if (coordinate.longitude == null || coordinate.latitude == null) {
            Log.e("MyLog", "Not received coordinate")
            return null
        } else {
            val geocoder = Geocoder(context, java.util.Locale.ENGLISH)
            val address = geocoder.getFromLocation(coordinate.latitude, coordinate.longitude, 1)
            Log.d("MyLog", "Locale city: ${address?.get(0)?.locality}")
            val city = address?.firstOrNull()?.locality.toString()
            weatherCache.saveCity(city, context)
            return city
        }
    }
}