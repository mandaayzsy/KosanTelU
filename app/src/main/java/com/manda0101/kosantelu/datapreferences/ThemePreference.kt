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

val Context.dataStore by preferencesDataStore(name = "settings")

val THEME_KEY = stringPreferencesKey("theme")

// Fungsi untuk menyimpan preferensi tema
@Composable
fun SaveThemePreference(theme: String) {
    val context = LocalContext.current
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

    LaunchedEffect(Unit) {
        val preferences = context.dataStore.data.first()
        theme = preferences[THEME_KEY] ?: "light"
    }
    return theme
}
