package com.example.natifetask9.ui.fragments.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.natifetask9.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    fun connectToServer(userName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.connectToServer(userName)
        }
    }

}