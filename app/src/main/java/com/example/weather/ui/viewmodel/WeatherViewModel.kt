package com.example.weather.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.model.WeatherInfo
import com.example.weather.logic.repository.geo.interfaces.GeolocationRepository
import com.example.weather.logic.repository.weather.interfaces.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val geolocationRepository: GeolocationRepository,
) : ViewModel() {
    private val _weatherInfoState: MutableLiveData<WeatherInfo?> =
        MutableLiveData<WeatherInfo?>(null)
    val weatherInfoState: LiveData<WeatherInfo?> = _weatherInfoState

    private val _dialogState: MutableLiveData<Boolean> = MutableLiveData(false)
    val dialogState: LiveData<Boolean> = _dialogState

    private val _cityState: MutableLiveData<String> = MutableLiveData("")
    val cityState: LiveData<String> = _cityState

    private val _isLoadingState: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoadingState: LiveData<Boolean> = _isLoadingState

    fun loadWeather(city: String, context: Context) {
        _isLoadingState.value = true
        viewModelScope.launch {
            try {
                var weatherInfo: WeatherInfo
                var json: String? = null
                json = weatherRepository.readWeatherData(context)
                if (json == null) {
                    weatherInfo = weatherRepository.fetchAndParseWeather(city, context)
                } else {
                    weatherInfo = weatherRepository.parseWeather(json)
                }
                _weatherInfoState.value = weatherInfo
                if (_cityState.value != city) {
                    _cityState.value = city
                }
            } catch (e: Exception) {
                Log.e("MyLog", "Error in loading weather data: ${e.message}")
            } finally {
                _isLoadingState.value = false
            }
        }
    }

    fun loadCity(context: Context) {
        _isLoadingState.value = true
        viewModelScope.launch {
            try {
                val cachedCity = weatherRepository.readUserCity(context)
                val city = cachedCity ?: geolocationRepository.getCity(context)

                if (!city.isNullOrEmpty() && _cityState.value != city) {
                    _cityState.value = city
                }
            } catch (e: Exception) {
                Log.e("MyLog", "Error loading city: ${e.message}", e)
            } finally {
                _isLoadingState.value = false
            }
        }
    }

    fun showDialog() {
        _dialogState.value = true
    }

    fun hideDialog() {
        _dialogState.value = false
    }

}