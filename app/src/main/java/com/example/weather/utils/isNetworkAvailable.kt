package com.example.weather.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun isNetWorkAvailable(context: Context): Boolean{
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    //получаем текущее соединение
    val network = cm.activeNetwork ?: return false

    //проверяем возможности сети
    val capabilities = cm.getNetworkCapabilities(network) ?: return false

    // проверяем, есть ли интернет-способность
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

}