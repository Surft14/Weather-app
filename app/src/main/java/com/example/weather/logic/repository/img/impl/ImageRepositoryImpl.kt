package com.example.weather.logic.repository.img.impl

import android.content.Context
import android.graphics.Bitmap
import com.example.weather.logic.image.impl.ImageServiceImpl
import com.example.weather.logic.image.interfaces.ImageService
import com.example.weather.logic.repository.img.interfaces.ImageRepository

class ImageRepositoryImpl(
    private val imageService: ImageService = ImageServiceImpl()
): ImageRepository {
    override suspend fun bitmapToBase64(bitmap: Bitmap): String {
        return imageService.bitmapToBase64(bitmap)
    }

    override suspend fun base64ToBitmap(encodedString: String): Bitmap {
        return imageService.base64ToBitmap(encodedString)
    }

    override suspend fun downloadImage(
        imageUrl: String,
    ): Bitmap? {
        return imageService.downloadImage(imageUrl)
    }
}