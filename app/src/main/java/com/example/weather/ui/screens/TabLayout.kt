package com.example.weather.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRowDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weather.data.model.WeatherForecast
import com.example.weather.data.model.WeatherHour
import com.example.weather.ui.screens.item.DayItem
import com.example.weather.ui.screens.item.HourItem
import com.example.weather.ui.theme.BlueLight
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabLayout(daysList: List<WeatherForecast>, hourList: List<WeatherHour>) {// Находиться ниже Main card
    val tabList = listOf("Hours", "Days")
    val pagerState = rememberPagerState(
        pageCount = { tabList.size }
    )
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp)
            .clip(RoundedCornerShape(8.dp)),
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            indicator = { position ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(position[tabIndex]),
                    height = 2.dp,
                    color = Color.White
                )
            },
            contentColor = Color.White,
            containerColor = BlueLight,
        ) {
            tabList.forEachIndexed { index, str ->
                Tab(
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(text = str) },
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1.0f)
        ) { Index ->
            Log.i("MyLog", hourList.toString())
            val currentList = when(Index){
                0 -> LazyColumn {
                    items(hourList) { HourItem(it) }

                }
                1 -> LazyColumn {
                    items(daysList) { DayItem(it) }
                }
                else -> LazyColumn {
                    items(daysList) { DayItem(it) }
                }
            }
        }
    }
}