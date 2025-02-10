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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.weather.screens.MainCard
import com.example.weather.screens.TabLayout
import com.example.weather.ui.theme.WeatherTheme

const val API_KEY = "1a86c614dfa6db6e65f778d79fe2e131"
const val UNITS = "metric"
const val LANGUAGE = "ru"



class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherTheme {
                Image(
                    painter = painterResource(R.drawable.skybox),
                    contentDescription = "Background blue sky",
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.7f),
                    contentScale = ContentScale.FillBounds,
                )
                Column {
                    MainCard("Cheboksary", this@MainActivity)
                    TabLayout()
                }
            }
        }
    }
}



