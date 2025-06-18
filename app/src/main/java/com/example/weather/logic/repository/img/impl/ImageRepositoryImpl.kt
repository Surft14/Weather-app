package com.example.weather.logic.repository.img.impl

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.example.weather.logic.image.impl.ImageServiceImpl
import com.example.weather.logic.image.interfaces.ImageService
import com.example.weather.logic.repository.img.interfaces.ImageRepository

class ImageRepositoryImpl(
    private val imageService: ImageService = ImageServiceImpl(),
) : ImageRepository {
    override suspend fun bitmapToBase64(bitmap: Bitmap?): String {
        Log.d("MyLog", "Repository image bitmapToBase64 start")
        return imageService.bitmapToBase64(bitmap)
    }

    override suspend fun base64ToBitmap(encodedString: String?): Bitmap? {
        Log.d("MyLog", "Repository image base64ToBitmap start")
        return imageService.base64ToBitmap(encodedString)
    }

    override suspend fun downloadImage(imageUrl: String, context: Context): Bitmap? {
        Log.d("MyLog", "Repository image downloadImage start")
        return imageService.downloadImage(imageUrl, context)
    }
}