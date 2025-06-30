package com.example.weather.utils

import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

object GeolocationUtils {
    private val permissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
    )

    @Composable
    fun getGeolocationPermission(onPermissionGranted: () -> Unit) {
        val context = LocalContext.current

        val pLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions()
        ) { isGranted ->
            val granted = permissions.any { isGranted[it] == true }
            if (granted) {
                Log.d("MyLog", "Permissions granted")
                onPermissionGranted()
            } else {
                Log.w("MyLog", "Permissions denied")
            }
        }

        // Запрашиваем разрешение после монтирования
        LaunchedEffect(Unit) {
            val allGranted = permissions.all {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }
            if (allGranted) {
                Log.d("MyLog", "Permissions already granted")
                onPermissionGranted()
            } else {
                Log.d("MyLog", "Requesting permissions...")
                pLauncher.launch(permissions)
            }
        }
    }
}