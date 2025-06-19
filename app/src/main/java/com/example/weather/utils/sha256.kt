package com.example.weather.utils

import java.security.MessageDigest

fun String.sha256(): String {
    val digest = MessageDigest.getInstance("SHA-256")
    val bytes = digest.digest(this.toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}