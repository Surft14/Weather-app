package com.example.weather

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
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
import com.example.weather.data.weather.readUserCity
import com.example.weather.data.weather.readWeatherData
import com.example.weather.logic.GeolocationLogic.getCityFromCoordinate
import com.example.weather.ui.screens.MainCard
import com.example.weather.ui.screens.TabLayout
import com.example.weather.ui.screens.DialogSearch
import com.example.weather.ui.theme.WeatherTheme
import com.example.weather.utils.GeolocationUtils
import com.example.weather.logic.weather.getData
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

                LaunchedEffect(Unit) {
                    readUserCity(this@MainActivity, city)
                    if (city.value.isBlank()){
                        if (ContextCompat.checkSelfPermission(
                                this@MainActivity,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(
                                this@MainActivity,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            city.value = getCityFromCoordinate(this@MainActivity) ?: "Moscow"
                        }
                    }
                }

                LaunchedEffect(city.value) {
                    if (city.value.isNotBlank() && isNetWorkAvailable(this@MainActivity)){
                        getData(city.value, this@MainActivity, daysList, day)
                    } else if(city.value.isNotBlank()){
                        readWeatherData(this@MainActivity, day, daysList)
                    }
                }
                val dialogState = remember {
                    mutableStateOf(false)
                }
                if (dialogState.value) {
                    DialogSearch(dialogState, onSubmit = { city ->
                        getData(city, this, daysList, day)
                    })
                }


                Image(
                    painter = painterResource(R.drawable.skybox),
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



