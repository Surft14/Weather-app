package com.example.weather.logic.image.interfaces

import android.content.Context
import android.graphics.Bitmap

interface ImageService {
    suspend fun bitmapToBase64(bitmap: Bitmap?): String
    suspend fun base64ToBitmap(encodedString: String): Bitmap

    suspend fun downloadImage(imageUrl: String, context: Context): Bitmap?
}