package com.example.weather.ui.screens

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import com.example.weather.R

@Composable
fun Background(image: State<Bitmap?>) {
    Log.d("MyLog", "BackRound start with value")
    if (image != null){
        Image(
            bitmap = image.value?.asImageBitmap() ?: ImageBitmap.imageResource(R.drawable.skybox),
            contentDescription = "Background",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.7f),
            contentScale = ContentScale.FillBounds,
        )
    }
    Log.d("MyLog", "BackRound end with value")

}
