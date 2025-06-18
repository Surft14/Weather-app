package com.example.weather.logic.repository.img.interfaces

import android.graphics.Bitmap

interface ImageRepository {
    suspend fun bitmapToBase64(bitmap: Bitmap?): String
    suspend fun base64ToBitmap(encodedString: String): Bitmap

    suspend fun downloadImage(imageUrl: String): Bitmap?
}