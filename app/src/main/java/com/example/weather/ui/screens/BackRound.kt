package com.example.weather.ui.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.compose.LocalImageLoader
import com.example.weather.R
import com.example.weather.logic.network.provideUnsafeImageLoader

@Composable
fun BackRound(imageSkyBox: String, context: Context) {
    Log.d("MyLog", "BackRound start with value: ${imageSkyBox}")
    CompositionLocalProvider(
        LocalImageLoader provides provideUnsafeImageLoader(context)
    ) {
        AsyncImage(
            model = "https://85.234.7.243:8443/img/v1/weatherimg/${imageSkyBox}.png",
            contentDescription = "Background",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.7f),
            contentScale = ContentScale.FillBounds,
            error = painterResource(R.drawable.skybox),
            placeholder = painterResource(R.drawable.skybox),
        )
    }
    Log.d("MyLog", "BackRound end with value: ${imageSkyBox}")

}
