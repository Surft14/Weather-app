package com.example.weather.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.weather.R

@Composable
fun BackRound(value: String) {
    AsyncImage(
        model = "https://85.234.7.243:8443/img/v1/weatherimg/${value}.png",
        contentDescription = "Background",
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.7f),
        contentScale = ContentScale.FillBounds,
        error = painterResource(R.drawable.skybox),
        placeholder = painterResource(R.drawable.skybox)
    )
}