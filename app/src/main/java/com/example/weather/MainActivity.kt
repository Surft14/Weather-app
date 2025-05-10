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
import com.example.weather.data.WeatherInfo
import com.example.weather.data.const.WeatherCodes
import com.example.weather.data.weather.readUserCity
import com.example.weather.data.weather.readWeatherData
import com.example.weather.logic.GeolocationLogic.getCityFromCoordinate
import com.example.weather.ui.screens.MainCard
import com.example.weather.ui.screens.TabLayout
import com.example.weather.ui.screens.DialogSearch
import com.example.weather.ui.theme.WeatherTheme
import com.example.weather.utils.GeolocationUtils
import com.example.weather.logic.weather.getData
import com.example.weather.logic.weather.getWeatherCondition
import com.example.weather.utils.isNetWorkAvailable


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WeatherTheme {
                GeolocationUtils.GetGeolocationPermission()
                val city = remember { mutableStateOf("") }
                val daysList = remember {
                    mutableStateOf(listOf<WeatherInfo>())
                }
                val day = remember {
                    mutableStateOf(WeatherInfo())
                }
                val imageSkyBox = remember { mutableStateOf(R.drawable.skybox) }
                LaunchedEffect(Unit) {
                    readUserCity(this@MainActivity, city)
                    Log.d("MyLog", "City location from dataStore: ${city.value}")
                    if (city.value.isBlank()){
                        Log.i("MyLog", "City is blank")
                        if (ContextCompat.checkSelfPermission(
                                this@MainActivity,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(
                                this@MainActivity,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            Log.i("MyLog", "get city from geolocation")
                            city.value = getCityFromCoordinate(this@MainActivity) ?: "Moscow"
                        }
                    }
                }

                LaunchedEffect(city.value) {
                    Log.i("MyLog", "read data from cache")
                    readWeatherData(this@MainActivity, day, daysList)
                    if (day.value.equals(WeatherInfo()) && daysList.value.isEmpty() && isNetWorkAvailable(this@MainActivity)){
                        Log.i("MyLog", "cache is blank")
                        getData(city.value, this@MainActivity, daysList, day)
                    }
                    imageSkyBox.value = getWeatherCondition(day.value)
                }
                val dialogState = remember {
                    mutableStateOf(false)
                }
                if (dialogState.value) {
                    DialogSearch(dialogState, onSubmit = { city ->
                        getData(city, this, daysList, day)
                    })
                }


                imageSkyBox.value = getWeatherCondition(day.value)
                Image(
                    painter = painterResource(imageSkyBox.value),
                    contentDescription = "Background blue sky",
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.7f),
                    contentScale = ContentScale.FillBounds,
                )
                Column {
                    MainCard(
                        day,
                        onClickSync = {
                            getData(city.value, this@MainActivity, daysList, day)
                        },
                        onClickSearch = {
                            dialogState.value = true
                        }
                    )
                    TabLayout(daysList, day)
                }
            }
        }
    }
}



