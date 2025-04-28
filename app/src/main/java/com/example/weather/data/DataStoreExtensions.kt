package com.example.weather.data

// DataStoreExtensions.kt // подставьте свой пакет

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.core.DataStore
import com.example.weather.data.const.PreferencesKey

// Вот это — единственная строка, создающая Context.dataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Weather")
