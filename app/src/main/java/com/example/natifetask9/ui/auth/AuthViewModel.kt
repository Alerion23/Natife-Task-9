package com.example.natifetask9.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.natifetask9.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {


    fun connectToServer() {
        viewModelScope.launch(Dispatchers.Main) {
            repository.connectToServer()
        }
    }

}