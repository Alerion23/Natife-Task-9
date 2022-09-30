package com.example.natifetask9.ui.fragments.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.User
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserListViewModel(
    private val chatRepository: ChatRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _userList = MutableStateFlow<List<User>>(emptyList())
    val userList = _userList.asStateFlow()

    init {
        viewModelScope.launch {
            chatRepository.userList().collectLatest {
                _userList.emit(it)
            }
        }
        fetchUsers()
    }

    fun fetchUsers() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.Main) {
            chatRepository.getUsers()
        }
        _isLoading.value = false
    }

    fun logOutUser() {
        viewModelScope.launch(Dispatchers.Main) {
            authRepository.logOut()
        }
    }

}