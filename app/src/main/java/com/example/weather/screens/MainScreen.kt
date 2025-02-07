package com.example.weather.screens

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weather.R
import com.example.weather.weather.getIconWeather
import com.example.weather.ui.theme.BlueLight
import com.example.weather.weather.WeatherInfo
import com.example.weather.weather.getMinMaxTemp
import com.example.weather.weather.getWeatherInfo
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

//city: String = "London", context: Context
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MainScreen(city: String = "London", context: Context = TODO()) {


    val icon = remember {
        mutableStateOf("Unknown")
    }
    val weatherInfo = remember {
        mutableStateOf(WeatherInfo())
    }

    val dateNow = remember {
        mutableStateOf("Unknown")
    }

    getMinMaxTemp(city, weatherInfo, context)
    getIconWeather(city, icon, context)
    getWeatherInfo(city, weatherInfo, context)

    Log.i("MyLog", "MainScreen: temp: ${weatherInfo.value.temp}")
    Log.i("MyLog", "MainScreen: feelLike: ${weatherInfo.value.feelLike}")
    Log.i("MyLog", "MainScreen: weather: ${weatherInfo.value.weather}")
    Log.i("MyLog", "MainScreen: Max: ${weatherInfo.value.tempMax}")
    Log.i("MyLog", "MainScreen: min: ${weatherInfo.value.tempMin}")

    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH)

    dateNow.value = currentDate.format(formatter)
    Image(
        painter = painterResource(R.drawable.skybox),
        contentDescription = "Background blue sky",
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.7f),
        contentScale = ContentScale.FillBounds,
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = BlueLight,
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            shape = RoundedCornerShape(11.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        dateNow.value,
                        modifier = Modifier.padding(top = 9.dp, start = 8.dp),
                        style = TextStyle(fontSize = 15.sp),
                    )
                    AsyncImage(
                        model = "https://openweathermap.org/img/wn/${icon.value}@2x.png",
                        contentDescription = "icon weather",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(top = 3.dp, end = 8.dp),
                    )
                }
                Text(
                    city,
                    style = TextStyle(fontSize = 24.sp),
                )
                Text(
                    "${weatherInfo.value.temp}ºC / ${weatherInfo.value.feelLike}ºC",
                    style = TextStyle(fontSize = 40.sp),
                    modifier = Modifier.padding(10.dp),
                )
                Text(
                    weatherInfo.value.weather,
                    style = TextStyle(fontSize = 16.sp),
                )
                Text(
                    "${weatherInfo.value.tempMax}ºC / ${weatherInfo.value.tempMin}ºC",
                    style = TextStyle(fontSize = 20.sp),
                )
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    IconButton(onClick = {

                    }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_search_24),
                            contentDescription = "Search",
                            )
                    }
                    IconButton(onClick = {

                    }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_cloud_sync_24),
                            contentDescription = "Sync cloud",
                        )
                    }
                }
                /*Text(
                    test.value,
                    style = TextStyle(fontSize = 20.sp),
                )*/
            }
        }
    }
}