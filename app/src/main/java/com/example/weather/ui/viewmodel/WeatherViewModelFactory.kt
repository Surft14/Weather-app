package com.example.weather.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather.logic.repository.geo.interfaces.GeolocationRepository
import com.example.weather.logic.repository.weather.interfaces.WeatherRepository

class WeatherViewModelFactory(
    private val weatherRepository: WeatherRepository,
    private val geolocationRepository: GeolocationRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(weatherRepository, geolocationRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}