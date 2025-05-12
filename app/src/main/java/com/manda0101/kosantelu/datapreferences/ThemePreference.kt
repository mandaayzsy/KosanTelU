package com.manda0101.kosantelu.datapreferences

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.dataStore by preferencesDataStore(name = "settings")
val THEME_KEY = stringPreferencesKey("theme")

@Composable
fun getThemePreference(): Boolean {
    val context = LocalContext.current
    var darkTheme by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val preferences = context.dataStore.data.first()
        val theme = preferences[THEME_KEY] ?: "light"
        darkTheme = theme == "dark"
    }

    return darkTheme
}

