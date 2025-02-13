package com.example.weather

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.weather.data.WeatherInfo
import com.example.weather.screens.MainCard
import com.example.weather.screens.TabLayout
import com.example.weather.ui.theme.WeatherTheme
import com.example.weather.weather.getData
import com.example.weather.weather.getWeatherInfo


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherTheme {
                val daysList = remember {
                    mutableStateOf(listOf<WeatherInfo>())
                }
                val day = remember {
                    mutableStateOf(WeatherInfo())
                }
                getData("Cheboksary", this, daysList, day)
                Image(
                    painter = painterResource(R.drawable.skybox),
                    contentDescription = "Background blue sky",
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.7f),
                    contentScale = ContentScale.FillBounds,
                )
                Column {
                    MainCard(day)
                    TabLayout(daysList, day)
                }
            }
        }
    }
}



