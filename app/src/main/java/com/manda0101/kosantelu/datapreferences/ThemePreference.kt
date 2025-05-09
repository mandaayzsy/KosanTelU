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
val THEME_KEY = stringPreferencesKey("theme")

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
fun getThemePreference(): Boolean {
    val context = LocalContext.current
    var darkTheme by remember { mutableStateOf(false) }

    // Mengambil preferensi tema dari DataStore
    LaunchedEffect(Unit) {
        val preferences = context.dataStore.data.first()
        val theme = preferences[THEME_KEY] ?: "light"
        darkTheme = theme == "dark"
    }

    return darkTheme
}

