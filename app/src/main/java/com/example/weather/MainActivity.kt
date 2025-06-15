package com.example.weather

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.weather.data.model.WeatherNow
import com.example.weather.logic.repository.geo.impl.GeolocationRepositoryImpl
import com.example.weather.logic.repository.weather.impl.WeatherRepositoryImpl
import com.example.weather.ui.screens.DialogSearch
import com.example.weather.ui.screens.MainCard
import com.example.weather.ui.screens.TabLayout
import com.example.weather.ui.theme.WeatherTheme
import com.example.weather.ui.viewmodel.WeatherViewModel
import com.example.weather.ui.viewmodel.WeatherViewModelFactory
import com.example.weather.utils.GeolocationUtils
import com.example.weather.utils.getWeatherCondition
import com.example.weather.utils.isNetWorkAvailable


class MainActivity : ComponentActivity() {
    lateinit var viewModel: WeatherViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val weatherRepo = WeatherRepositoryImpl()
        val geoRepo = GeolocationRepositoryImpl()

        val factory = WeatherViewModelFactory(weatherRepo, geoRepo)

        viewModel = ViewModelProvider(this, factory)[WeatherViewModel::class.java]



        setContent {
            WeatherTheme {
                GeolocationUtils.GetGeolocationPermission()

                val imageSkyBox = remember { mutableStateOf(R.drawable.skybox) }
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
                        }
                    }
                }

                LaunchedEffect(viewModel.cityState.value) {
                    if (viewModel.weatherInfoState.value == null && isNetWorkAvailable(
                            this@MainActivity
                        )
                    ) {
                        Log.i("MyLog", "MainActivity: loading city and weather data with viewmodel")
                        viewModel.loadCityAndWeather(this@MainActivity)
                    }

                }

                Box() {
                    imageSkyBox.value = getWeatherCondition(viewModel.weatherInfoState.value)
                    if (viewModel.dialogState.value == true) {
                        DialogSearch(viewModel, onSubmit = { city ->
                            viewModel.loadWeather(city, this@MainActivity)
                        })
                    }
                    Log.i("MyLog", "MainActivity: start change image background")
                    Image(
                        painter = painterResource(imageSkyBox.value),
                        contentDescription = "Background blue sky",
                        modifier = Modifier
                            .fillMaxSize()
                            .alpha(0.7f),
                        contentScale = ContentScale.FillBounds,
                    )
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



