package com.manda0101.kosantelu.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manda0101.kosantelu.database.UserRepository
import com.manda0101.kosantelu.model.User
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    fun login(email: String, password: String, onResult: (User?) -> Unit) {
        viewModelScope.launch {
            val user = repository.getUserByEmailAndPassword(email, password)
            onResult(user)
        }
    }
}
