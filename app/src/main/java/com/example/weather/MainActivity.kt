package com.example.weather

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.LaunchedEffect
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.weather.data.model.WeatherNow
import com.example.weather.logic.repository.geo.impl.GeolocationRepositoryImpl
import com.example.weather.logic.repository.img.impl.ImageRepositoryImpl
import com.example.weather.logic.repository.weather.impl.WeatherRepositoryImpl
import com.example.weather.ui.screens.Background
import com.example.weather.ui.screens.DialogSearch
import com.example.weather.ui.screens.MainCard
import com.example.weather.ui.screens.TabLayout
import com.example.weather.ui.theme.WeatherTheme
import com.example.weather.ui.viewmodel.WeatherViewModel
import com.example.weather.ui.viewmodel.WeatherViewModelFactory
import com.example.weather.utils.GeolocationUtils
import com.example.weather.utils.isNetWorkAvailable


class MainActivity : ComponentActivity() {
    lateinit var viewModel: WeatherViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val weatherRepo = WeatherRepositoryImpl()
        val geoRepo = GeolocationRepositoryImpl()
        val imgRepo = ImageRepositoryImpl()

        val factory = WeatherViewModelFactory(weatherRepo, geoRepo, imgRepo)

        viewModel = ViewModelProvider(this, factory)[WeatherViewModel::class.java]



        setContent {
            WeatherTheme {
                GeolocationUtils.GetGeolocationPermission()
                LaunchedEffect(Unit) {
                    if (viewModel.cityState.value.isNullOrBlank()) {
                        Log.i("MyLog", "MainActivity: City from ViewModel is blank")
                        if (ContextCompat.checkSelfPermission(
                                this@MainActivity,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(
                                this@MainActivity,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            Log.i("MyLog", "MainActivity: loading city with viewmodel")
                            viewModel.loadCity(this@MainActivity)

                            if (isNetWorkAvailable(this@MainActivity)) {
                                Log.i(
                                    "MyLog",
                                    "MainActivity: loading city and weather data with viewmodel (merged)"
                                )
                                viewModel.loadCityAndWeather(this@MainActivity)
                            }
                        }
                    }
                }

                Box {
                    if (viewModel.dialogState.value == true) {
                        DialogSearch(viewModel, onSubmit = { city ->
                            viewModel.searchWeather(city, this@MainActivity)
                        })
                    }
                    Log.i("MyLog", "MainActivity: start change image background")
                    LaunchedEffect(viewModel.imageBackState.value){
                        Log.i("MyLog", "MainActivity: info of background ${viewModel.imageBackState.value}")
                        val url =
                            "https://85.234.7.243:8443/img/v1/weatherimg/${viewModel.imageBackState.value}.png"
                        Log.i("MyLog", "MainActivity: info $url")
                        viewModel.loadImage(url, this@MainActivity)
                    }
                    Background(viewModel.bitmapState.value)
                    Column {
                        Log.i("MyLog", "MainActivity: start MainCard")
                        MainCard(
                            viewModel.weatherInfoState.value?.weatherNow ?: WeatherNow(),
                            onClickSync = {
                                viewModel.refreshWeather(this@MainActivity)
                            },
                            onClickSearch = {
                                viewModel.showDialog()
                            }
                        )
                        Log.i("MyLog", "MainActivity: start TabLayout")
                        TabLayout(
                            viewModel.weatherInfoState.value?.listWeatherForecast ?: listOf(),
                            viewModel.weatherInfoState.value?.listWeatherHour ?: listOf(),
                        )
                    }
                }

            }
        }
    }
}



