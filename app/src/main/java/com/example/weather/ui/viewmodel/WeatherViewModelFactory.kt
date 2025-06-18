package com.example.weather.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather.logic.repository.geo.interfaces.GeolocationRepository
import com.example.weather.logic.repository.img.interfaces.ImageRepository
import com.example.weather.logic.repository.weather.interfaces.WeatherRepository

class WeatherViewModelFactory(
    private val weatherRepository: WeatherRepository,
    private val geolocationRepository: GeolocationRepository,
    private val imageRepository: ImageRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(weatherRepository, geolocationRepository, imageRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}