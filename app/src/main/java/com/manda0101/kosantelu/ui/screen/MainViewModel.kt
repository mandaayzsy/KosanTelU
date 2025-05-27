package com.manda0101.kosantelu.ui.screen

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manda0101.kosantelu.database.KosanRepository
import com.manda0101.kosantelu.model.Kosan
import com.manda0101.kosantelu.model.RecycleBin
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import com.manda0101.kosantelu.datapreferences.ThemePreferencesManager

class MainViewModel(private val repository: KosanRepository) : ViewModel() {

    val allKosans: Flow<List<Kosan>> = repository.allKosans
    val allDeletedKosans: Flow<List<RecycleBin>> = repository.getAllDeletedKosans()

    fun insert(kosan: Kosan) {
        viewModelScope.launch { repository.insert(kosan) }
    }

    fun update(kosan: Kosan) {
        viewModelScope.launch { repository.update(kosan) }
    }

    fun delete(kosan: Kosan) {
        viewModelScope.launch { repository.delete(kosan) }
    }

    fun restoreKosan(recycleBin: RecycleBin) {
        viewModelScope.launch { repository.restoreKosan(recycleBin) }
    }

    fun permanentlyDeleteKosan(recycleBin: RecycleBin) {
        viewModelScope.launch { repository.permanentlyDeleteKosan(recycleBin) }
    }

    fun getKosanById(id: Long): Flow<Kosan?> {
        Log.d("MainViewModel", "Fetching Kosan with ID: $id")
        return repository.getKosanById(id)
    }

    private lateinit var _themePreferencesManager: ThemePreferencesManager

    fun initializeThemePreferencesManager(context: Context) {
        Log.d("KosanTeluPrefs", "MainViewModel: initializeThemePreferencesManager called.")
        if (!::_themePreferencesManager.isInitialized) {
            _themePreferencesManager = ThemePreferencesManager(context)
            Log.d("KosanTeluPrefs", "MainViewModel: ThemePreferencesManager initialized.")
        } else {
            Log.d("KosanTeluPrefs", "MainViewModel: ThemePreferencesManager already initialized.")
        }
    }

    val currentLayoutPreference: Flow<Boolean>
        get() {
            if (::_themePreferencesManager.isInitialized) {
                Log.d("KosanTeluPrefs", "MainViewModel: Providing layout preference Flow from initialized manager.")
                return _themePreferencesManager.layoutPreferenceFlow
            } else {
                Log.e("KosanTeluPrefs", "MainViewModel: ThemePreferencesManager not initialized! Providing default Flow.")
                return MutableStateFlow(true) // Default ke true (list) jika belum diinisialisasi
            }
        }

    fun saveLayoutPreference(isLinearLayout: Boolean) {
        viewModelScope.launch {
            if (::_themePreferencesManager.isInitialized) {
                Log.d("KosanTeluPrefs", "MainViewModel: Attempting to save preference: $isLinearLayout")
                _themePreferencesManager.saveLayoutPreference(isLinearLayout)
            } else {
                Log.e("KosanTeluPrefs", "MainViewModel: ThemePreferencesManager not initialized! Cannot save preference.")
            }
        }
    }
}