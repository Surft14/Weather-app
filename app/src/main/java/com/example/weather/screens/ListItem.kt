package com.example.weather.screens

import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weather.data.WeatherInfo
import com.example.weather.ui.theme.BlueLight

@Composable
fun ListItem(item: WeatherInfo, day: MutableState<WeatherInfo>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp)
            .clickable {
                if (item.hours.isEmpty()) return@clickable
                day.value = item
            },
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
                    item.time,
                    color = Color.White,
                    style = TextStyle(fontSize = 20.sp)
                )
                Row(){
                    Text(
                        item.weather,
                        color = Color.White,
                        style = TextStyle(fontSize = 20.sp)
                    )
                    Text(
                        item.wind + " kph",
                        color = Color.White,
                        style = TextStyle(fontSize = 20.sp),
                        modifier = Modifier.padding(start = 9.dp),
                    )
                }
            }
            Text(
                "${item.temp.ifEmpty{item.tempMax+"/"+item.tempMin}}ºC",
                color = Color.White,
                style = TextStyle(fontSize = 30.sp)
            )
            AsyncImage(
                model = "https:" + item.icon,
                contentDescription = "icon weather",
                modifier = Modifier
                    .padding(end = 6.dp)
                    .size(45.dp)
            )
        }
    }
}