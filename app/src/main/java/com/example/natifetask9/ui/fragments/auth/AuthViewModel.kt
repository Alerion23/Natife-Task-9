package com.example.natifetask9.ui.fragments.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.natifetask9.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isConnected = MutableStateFlow(false)
    val isConnected = _isConnected.asStateFlow()

    fun connectToServer(userName: String) {
        _isLoading.tryEmit(true)
        viewModelScope.launch(Dispatchers.IO) {
            repository.connectToServer(userName)
            repository.authState().collectLatest {
                if (it) {
                    _isConnected.emit(it)
                    _isLoading.emit(false)
                }
            }
        }
    }

}