package com.example.weather.screens

import android.content.Context
import android.os.Build
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
import com.example.weather.getweather.getIconWeather
import com.example.weather.getweather.getTemp
import com.example.weather.getweather.getTempLikeFeels
import com.example.weather.ui.theme.BlueLight
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

//city: String = "London", context: Context
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MainScreen(city: String = "London", context: Context) {
    val icon = remember {
        mutableStateOf("Unknown")
    }
    val temp = remember {
        mutableStateOf("Unknown")
    }
    val fellLike = remember {
        mutableStateOf("Unknown")
    }
    val dateNow = remember {
        mutableStateOf("Unknown")
    }
    getIconWeather(city, icon, context)
    getTemp(city, temp, context)
    getTempLikeFeels(city, fellLike, context)
    val currentDate = LocalDate.now() // Текущая дата
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
                contentColor = Color.White),
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
                            .size(50.dp)
                            .padding(top = 3.dp, end = 8.dp),
                        )
                }
                Text(
                    "${temp.value} C / ${fellLike.value}",
                    style = TextStyle(fontSize = 30.sp),
                    modifier = Modifier.padding(10.dp),
                )
            }
        }
    }
}