package com.example.weather.logic.image.impl

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import com.example.weather.logic.image.interfaces.ImageService
import java.io.ByteArrayOutputStream
import java.net.URL

class ImageServiceImpl: ImageService {
    override suspend fun bitmapToBase64(bitmap: Bitmap): String {
        Log.d("MyLog", "Service image bitmapToBase64 start")
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    override suspend fun base64ToBitmap(encodedString: String): Bitmap {
        Log.d("MyLog", "Service image base64ToBitmap start")
        val bytes = Base64.decode(encodedString, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    override suspend fun downloadImage(imageUrl: String): Bitmap? {
        Log.d("MyLog", "Service image downloadImage start")
        return try {
            val url = URL(imageUrl)
            val inputStream = url.openStream()
            val bitmap = BitmapFactory.decodeStream(inputStream)
            bitmap
        } catch (e : Exception){
            e.printStackTrace()
            null
        }
    }

}