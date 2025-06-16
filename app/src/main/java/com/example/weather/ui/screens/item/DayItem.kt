package com.example.weather.ui.screens.item

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weather.data.model.WeatherForecast
import com.example.weather.ui.theme.BlueLight

@Composable
fun DayItem(day: WeatherForecast) {
    Log.d("MyLog", "DayItem start")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp),
        backgroundColor = BlueLight,
        elevation = 0.dp,
        shape = RoundedCornerShape(5.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(start = 6.dp, top = 3.dp, bottom = 4.dp)
            ) {
                Text(
                    day.date,
                    color = Color.White,
                    style = TextStyle(fontSize = 20.sp)
                )
                Row() {
                    Text(
                        day.text,
                        color = Color.White,
                        style = TextStyle(
                            fontSize = if (day.text.length > 20) {
                                10.sp
                            } else {
                                20.sp
                            }
                        )
                    )
                }
            }
            Text(
                "${day.avgTemp}ºC",
                color = Color.White,
                style = TextStyle(fontSize = 30.sp)
            )
            AsyncImage(
                model = "https:" + day.icon,
                contentDescription = "icon weather",
                modifier = Modifier
                    .padding(end = 6.dp)
                    .size(45.dp)
            )
        }
    }
}