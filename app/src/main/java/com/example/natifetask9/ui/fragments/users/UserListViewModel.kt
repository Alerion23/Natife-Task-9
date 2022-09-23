package com.example.natifetask9.ui.fragments.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.natifetask9.model.User
import com.example.natifetask9.repository.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserListViewModel(
    private val repository: ChatRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _userList = MutableStateFlow<List<User>>(emptyList())
    val userList = _userList.asStateFlow()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        _isLoading.tryEmit(true)
        viewModelScope.launch(Dispatchers.Main) {
            repository.getUsers()
            repository.userList().collectLatest {
                _userList.emit(it)
                _isLoading.emit(false)
            }
        }
    }

}