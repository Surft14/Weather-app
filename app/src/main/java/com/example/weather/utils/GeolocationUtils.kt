package com.example.weather.utils

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import java.util.jar.Manifest

object GeolocationUtils {
    private val permissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
    )

    @Composable
    fun GetGeolocationPermission() {
        val pLauncher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions())
            { isGranted ->
                permissions.forEach { perm ->
                    if (isGranted[perm] == true) {
                        Log.d("MyLog", "Permission $perm has been granted")
                    } else {
                        Log.w("MyLog", "Permission $perm has not been granted")
                    }
                }
            }

        SideEffect {
            pLauncher.launch(permissions)
        }
    }
}