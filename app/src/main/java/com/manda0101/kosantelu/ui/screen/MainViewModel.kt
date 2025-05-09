package com.manda0101.kosantelu.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manda0101.kosantelu.database.KosanRepository
import com.manda0101.kosantelu.model.Kosan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: KosanRepository) : ViewModel() {

    val allKosans = repository.allKosans

    fun insert(kosan: Kosan) {
        viewModelScope.launch {
            repository.insert(kosan)
        }
    }

    fun update(kosan: Kosan) {
        viewModelScope.launch {
            repository.update(kosan)
        }
    }

    fun delete(kosan: Kosan) {
        viewModelScope.launch {
            repository.delete(kosan)
        }
    }

    fun markAsDeleted(kosanId: Long) {
        viewModelScope.launch {
            repository.markAsDeleted(kosanId)
        }
    }


    fun getKosanById(id: Long): Flow<Kosan> {
        return repository.getKosanById(id)
    }
}