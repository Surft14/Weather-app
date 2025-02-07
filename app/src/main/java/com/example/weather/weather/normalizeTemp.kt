package com.example.weather.weather

fun normalizeTemperature(temp: Double): Double {
    return if (temp > 0 && temp < 1) {
        0.0 // Значение по умолчанию
    } else {
        temp
    }
}