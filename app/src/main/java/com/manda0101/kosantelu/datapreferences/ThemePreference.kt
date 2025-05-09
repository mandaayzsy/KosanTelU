package com.manda0101.kosantelu.datapreferences

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

// Pastikan hanya satu deklarasi `dataStore`
val Context.dataStore by preferencesDataStore(name = "settings")

// Fungsi untuk menyimpan tema yang dipilih
@Composable
fun SaveThemePreference(theme: String) {
    val context = LocalContext.current
    // Menggunakan LaunchedEffect untuk menjalankan coroutine
    LaunchedEffect(theme) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = theme
        }
    }
}

// Fungsi untuk mendapatkan preferensi tema
@Composable
fun getThemePreference(): String {
    val context = LocalContext.current
    var theme by remember { mutableStateOf("light") }

    // Menggunakan LaunchedEffect untuk mengambil data
    LaunchedEffect(Unit) {
        val preferences = context.dataStore.data.first()
        theme = preferences[THEME_KEY] ?: "light"
    }
    return theme
}

val THEME_KEY = stringPreferencesKey("theme")
