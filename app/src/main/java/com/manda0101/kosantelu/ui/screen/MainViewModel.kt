package com.manda0101.kosantelu.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manda0101.kosantelu.database.KosanRepository
import com.manda0101.kosantelu.model.Kosan
import com.manda0101.kosantelu.model.RecycleBin
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow

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

    fun getKosanById(id: Long): Flow<Kosan> {
        Log.d("MainViewModel", "Fetching Kosan with ID: $id")
        return repository.getKosanById(id)
    }

}
