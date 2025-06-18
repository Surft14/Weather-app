package com.example.weather.logic.image.impl

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import com.example.weather.logic.image.interfaces.ImageService
import com.example.weather.logic.network.CustomHurlStack
import java.io.ByteArrayOutputStream
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ImageServiceImpl : ImageService {
    override suspend fun bitmapToBase64(bitmap: Bitmap?): String {
        Log.d("MyLog", "Service image bitmapToBase64 start")
        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    override suspend fun base64ToBitmap(encodedString: String?): Bitmap? {
        Log.d("MyLog", "Service image base64ToBitmap start")
        if (encodedString.isNullOrEmpty()){
            val bytes = Base64.decode(encodedString, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }else{
            return null
        }
    }

    override suspend fun downloadImage(imageUrl: String, context: Context): Bitmap? = suspendCoroutine{ continuation ->
        Log.d("MyLog", "Service image downloadImage start")
        val requestQueue = Volley.newRequestQueue(context, CustomHurlStack(context))

        val imageRequest =  ImageRequest(
            imageUrl,
            { bitmap -> continuation.resume(bitmap) },
            0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565,
            { error ->
                Log.e("MyLog", "Volley error: ${error.message}")
                continuation.resume(null)
            }
        )

        requestQueue.add(imageRequest)
    }

}