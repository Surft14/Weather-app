package com.example.weather.ui.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.model.WeatherInfo
import com.example.weather.logic.cache.CachedWeather
import com.example.weather.logic.repository.geo.interfaces.GeolocationRepository
import com.example.weather.logic.repository.img.interfaces.ImageRepository
import com.example.weather.logic.repository.weather.interfaces.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val geolocationRepository: GeolocationRepository,
    private val imageRepository: ImageRepository,
) : ViewModel() {
    private val _weatherInfoState = mutableStateOf<WeatherInfo?>(null)
    val weatherInfoState: State<WeatherInfo?> = _weatherInfoState

    private val _dialogState = mutableStateOf(false)
    val dialogState: State<Boolean> = _dialogState

    private val _cityState = mutableStateOf("")
    val cityState: State<String> = _cityState

    private val _isLoadingState = mutableStateOf(false)
    val isLoadingState: State<Boolean> = _isLoadingState

    private val _errorState = mutableStateOf<String?>("")
    val errorState: State<String?> = _errorState

    private val _imageBackState = mutableStateOf("skybox")
    val imageBackState: State<String> = _imageBackState

    private val _bitmapState = mutableStateOf<Bitmap?>(null)
    val bitmapState: State<Bitmap?> = _bitmapState

    init {
        loadFromCache()
    }

    fun loadWeather(city: String, context: Context) {
        Log.d("MyLog", "ViewModel loadWeather start")
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
                loadCodeImage(weatherInfo)
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

    fun searchWeather(city: String, context: Context) {
        Log.d("MyLog", "ViewModel searchWeather start")
        _isLoadingState.value = true
        viewModelScope.launch {
            try {
                var weatherInfo: WeatherInfo =
                    weatherRepository.searchAndParseWeather(city, context)
                _weatherInfoState.value = weatherInfo
                loadCodeImage(weatherInfo)
            } catch (e: Exception) {
                Log.e("MyLog", "Error in search weather data: ${e.message}")
                _errorState.value = "Error in search weather data  ${e.message}"
            } finally {
                _isLoadingState.value = false
            }
        }
    }

    fun refreshWeather(context: Context) {
        Log.d("MyLog", "ViewModel refreshWeather start")
        val city = _cityState.value ?: return
        _isLoadingState.value = true
        viewModelScope.launch {
            try {
                val weatherInfo = weatherRepository.fetchAndParseWeather(city, context)
                _weatherInfoState.value = weatherInfo
                loadCodeImage(weatherInfo)
            } catch (e: Exception) {
                Log.e("MyLog", "Error refreshing weather: ${e.message}")
                _errorState.value = "Error refreshing weather ${e.message}"
            } finally {
                _isLoadingState.value = false
            }
        }
    }

    fun loadCityAndWeather(context: Context) {
        Log.d("MyLog", "ViewModel loadCityAndWeather start")
        _isLoadingState.value = true
        viewModelScope.launch {
            try {
                val city = weatherRepository.readUserCity(context)
                    ?: geolocationRepository.getCity(context)

                if (_cityState.value == city) {
                    weatherRepository.saveCity(city.toString(), context)
                }
                val json = weatherRepository.readWeatherData(context)
                if (!city.isNullOrEmpty() && json == null) {
                    _cityState.value = city
                    val weatherInfo = weatherRepository.fetchAndParseWeather(city, context)
                    _weatherInfoState.value = weatherInfo
                    loadCodeImage(weatherInfo)
                } else if (json != null) {
                    val weatherInfo = weatherRepository.parseWeather(json)
                    _weatherInfoState.value = weatherInfo
                    loadCodeImage(weatherInfo)
                } else {
                    Log.e("MyLog", "Error load city")
                    _errorState.value = "Couldn't identify the city"
                }
            } catch (e: Exception) {
                Log.e("MyLog", "Error load city and weather ${e.message}")
                _errorState.value = "Error loading city or weather ${e.message}"
            } finally {
                _isLoadingState.value = false
            }
        }
    }

    fun loadCity(context: Context) {
        Log.d("MyLog", "ViewModel loadCity start")
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
                Log.e("MyLog", "Error loading city: ${e.message}")
                _errorState.value = "Error loading city ${e.message}"
            } finally {
                _isLoadingState.value = false
            }
        }
    }

    fun loadCodeImage(day: WeatherInfo) {
        Log.d("MyLog", "ViewModel loadCodeImage start")
        _isLoadingState.value = true
        viewModelScope.launch {
            try {
                _imageBackState.value = weatherRepository.getWeatherCondition(day)
            } catch (e: Exception) {
                Log.e("MyLog", "Error loadImageBack: ${e.message}")
                _errorState.value = "Error loadImageBack ${e.message}"
            } finally {
                _isLoadingState.value = false
            }
        }

    }

    fun loadImage(imageUrl: String, context: Context) {
        Log.d("MyLog", "ViewModel loadImage start")
        Log.i("MyLog", "ViewModel loadImage info: $imageUrl")
        _isLoadingState.value = true
        viewModelScope.launch {
            try {
                var imageBase64 = weatherRepository.readBase64(context)
                if (imageBase64.isNullOrEmpty()) {
                    _bitmapState.value = imageRepository.downloadImage(imageUrl, context)
                    imageBase64 = imageRepository.bitmapToBase64(_bitmapState.value)
                    weatherRepository.saveBase64(imageBase64, context)
                } else {
                    _bitmapState.value = imageRepository.base64ToBitmap(imageBase64)
                }
            } catch (e: Exception) {
                Log.e("MyLog", "error image: ${e.message}")
            } finally {
                _isLoadingState.value = false
            }
        }
    }

    fun loadFromCache() {
        Log.d("MyLog", "ViewModel loadFromCache start")
        _isLoadingState.value = true
        viewModelScope.launch {
            try {
                val json = CachedWeather.weatherJSON
                if (!json.isNullOrEmpty()) {
                    _weatherInfoState.value = weatherRepository.parseWeather(json)
                }
                _cityState.value = CachedWeather.cityName ?: ""
                _bitmapState.value = imageRepository.base64ToBitmap(CachedWeather.imageBase64)
            } catch (e: Exception) {
                Log.e("MyLog", "error weather cache: ${e.message}")
            } finally {
                _isLoadingState.value = false
            }
        }
    }

    fun clearError() {
        Log.d("MyLog", "ViewModel clearError start")
        _errorState.value = null
    }

    fun showDialog() {
        Log.d("MyLog", "ViewModel showDialog start")
        _dialogState.value = true
    }

    fun hideDialog() {
        Log.d("MyLog", "ViewModel hideDialog start")
        _dialogState.value = false
    }

}