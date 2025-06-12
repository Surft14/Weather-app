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

    private val _errorState = MutableLiveData<String?>()
    val errorState: LiveData<String?> = _errorState

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
                    weatherInfo = weatherRepository.parseWeather(json, context)
                }
                _weatherInfoState.value = weatherInfo
                if (_cityState.value != city) {
                    _cityState.value = city
                }
            } catch (e: Exception) {
                Log.e("MyLog", "Error in loading weather data: ${e.message}")
                _errorState.value = "Error in loading weather data"
            } finally {
                _isLoadingState.value = false
            }
        }
    }

    fun refreshWeather(context: Context) {
        val city = _cityState.value ?: return
        _isLoadingState.value = true
        viewModelScope.launch {
            try {
                val weatherInfo = weatherRepository.fetchAndParseWeather(city, context)
                _weatherInfoState.value = weatherInfo
            } catch (e: Exception) {
                Log.e("MyLog", "Error refreshing weather: ${e.message}")
                _errorState.value = "Error refreshing weather"
            } finally {
                _isLoadingState.value = false
            }
        }
    }

    fun loadCityAndWeather(context: Context) {
        _isLoadingState.value = true
        viewModelScope.launch {
            try {
                val city = weatherRepository.readUserCity(context)
                    ?: geolocationRepository.getCity(context)
                weatherRepository.saveCity(city.toString(), context)

                if (!city.isNullOrEmpty()) {
                    _cityState.value = city
                    val weatherInfo = weatherRepository.fetchAndParseWeather(city, context)
                    _weatherInfoState.value = weatherInfo
                } else {
                    Log.e("MyLog", "Error load city")
                    _errorState.value = "Couldn't identify the city"
                }
            } catch (e: Exception) {
                Log.e("MyLog", "Error load city and weather")
                _errorState.value = "Error loading city or weather"
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
                    weatherRepository.saveCity(city, context)
                }
            } catch (e: Exception) {
                Log.e("MyLog", "Error loading city: ${e.message}", e)
                _errorState.value = "Error loading city"
            } finally {
                _isLoadingState.value = false
            }
        }
    }

    fun clearError() {
        _errorState.value = null
    }

    fun showDialog() {
        _dialogState.value = true
    }

    fun hideDialog() {
        _dialogState.value = false
    }

}