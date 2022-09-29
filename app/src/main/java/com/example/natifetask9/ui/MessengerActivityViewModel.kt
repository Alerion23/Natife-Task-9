package com.example.natifetask9.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.natifetask9.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MessengerActivityViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _isUserLoggedIn = MutableSharedFlow<Boolean>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val isUserLoggedIn = _isUserLoggedIn.asSharedFlow()

    init {
        checkIfUserLoggedIn()
        checkAuthState()
    }

    private fun checkAuthState() {
        viewModelScope.launch {
            repository.authState().collectLatest {
                if (it) {
                    _isUserLoggedIn.emit(true)
                }
            }
        }
    }

    private fun checkIfUserLoggedIn() {
        viewModelScope.launch(Dispatchers.IO) {
            val userName = repository.getUserName()
            if (userName != null && userName.isNotEmpty()) {
                repository.connectToServer(userName)
            } else {
                _isUserLoggedIn.emit(false)
            }

        }
    }

}