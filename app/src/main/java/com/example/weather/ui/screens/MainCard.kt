package com.example.weather.ui.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weather.R
import com.example.weather.ui.theme.BlueLight
import com.example.weather.data.model.WeatherNow

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainCard(day: WeatherNow, onClickSync: () -> Unit, onClickSearch: () -> Unit) {
    Log.d("MyLog", "MainActivity: start MainCard")
    Column(
        modifier = Modifier
            .padding(4.dp),
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = BlueLight,
                contentColor = Color.White,
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
// Текст даты
                        day.dateTime,
                        modifier = Modifier.padding(top = 9.dp, start = 8.dp),
                        style = TextStyle(fontSize = 15.sp),
                    )
                    AsyncImage(
// Иконка погоды
                        model = "https:${day.icon}",
                        contentDescription = "icon weather",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(top = 3.dp, end = 8.dp),
                    )
                }
                Text(
                    day.city,
                    style = TextStyle(fontSize = 24.sp),
                )
                Text(
//Нынешняя температура и как ощущаеться
                    "${day.temp.ifEmpty { 0 }}ºC/feel ${day.feelLike.ifEmpty { 0 }}ºC",
                    style = TextStyle(fontSize = 40.sp),
                    modifier = Modifier.padding(10.dp),
                )
                Text(
// Погода
                    "${day.text}",
                    style = TextStyle(fontSize = 16.sp),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${day.speed} kph",
                        style = TextStyle(fontSize = 16.sp),
                        modifier = Modifier.padding(end = 10.dp, top = 5.dp),
                    )
                    Column(
                        modifier = Modifier.padding(start = 10.dp),
                    ) {
                        Icon(
                            painter = painterResource(
                                when (day.dir) {
                                    "N" -> R.drawable.n
                                    "NNE" -> R.drawable.nne
                                    "NE" -> R.drawable.ne
                                    "ENE" -> R.drawable.ene
                                    "E" -> R.drawable.e
                                    "ESE" -> R.drawable.ese
                                    "SE" -> R.drawable.se
                                    "SSE" -> R.drawable.sse
                                    "S" -> R.drawable.s
                                    "SSW" -> R.drawable.ssw
                                    "SW" -> R.drawable.sw
                                    "WSW" -> R.drawable.wsw
                                    "W" -> R.drawable.w
                                    "WNW" -> R.drawable.wnw
                                    "NW" -> R.drawable.nw
                                    "NNW" -> R.drawable.nnw
                                    else -> R.drawable.ic_question_mark
                                }
                            ),
                            contentDescription = "Icon wind dir",
                            modifier = Modifier.size(20.dp),
                        )
                        Text(
                            text = day.dir,
                            style = TextStyle(fontSize = 11.sp),
                            modifier = Modifier.padding(bottom = 10.dp),
                        )
                    }
                }
                Row(// Кнопки
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { //Кнопка поиска
                        onClickSearch.invoke()
                    }
                    ) {
                        Icon(
// Иконка поиска
                            painter = painterResource(R.drawable.baseline_search_24),
                            contentDescription = "Search",
                        )
                    }

                    IconButton(onClick = {// Кнопка обноления
                        onClickSync.invoke() // Вызывает переданную функцию
                    }
                    ) {
                        Icon(
// Иконка синхронизации
                            painter = painterResource(R.drawable.baseline_cloud_sync_24),
                            contentDescription = "Sync cloud",
                        )
                    }
                }
            }
        }
    }
    Log.d("MyLog", "MainCard end")
}