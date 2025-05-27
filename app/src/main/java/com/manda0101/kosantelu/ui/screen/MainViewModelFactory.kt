package com.manda0101.kosantelu.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.manda0101.kosantelu.database.KosanRepository

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val repository: KosanRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java))
            return MainViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
