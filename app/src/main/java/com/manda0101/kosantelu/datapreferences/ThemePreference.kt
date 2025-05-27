package com.manda0101.kosantelu.datapreferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import android.util.Log

val Context.layoutPreferenceDataStore: DataStore<Preferences> by preferencesDataStore(name = "kosantelu_layout_prefs")
private val IS_LINEAR_LAYOUT_KEY = booleanPreferencesKey("is_linear_layout")

class ThemePreferencesManager(private val context: Context) {

    val layoutPreferenceFlow: Flow<Boolean> = context.layoutPreferenceDataStore.data
        .map { preferences ->

            val isLinearLayout = preferences[IS_LINEAR_LAYOUT_KEY] ?: true
            Log.d("KosanTeluPrefs", "ThemePreferencesManager: Reading preference: $isLinearLayout")
            isLinearLayout
        }

    suspend fun saveLayoutPreference(isLinearLayout: Boolean) {
        context.layoutPreferenceDataStore.edit { preferences ->
            preferences[IS_LINEAR_LAYOUT_KEY] = isLinearLayout
            Log.d("KosanTeluPrefs", "ThemePreferencesManager: Saving preference: $isLinearLayout")
        }
    }
}