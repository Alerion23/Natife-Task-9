package com.example.natifetask9.ui.fragments.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.natifetask9.model.Message
import com.example.natifetask9.repository.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ChatViewModel(
    private val repository: ChatRepository,
    private val receiverId: String
) : ViewModel() {

    private val _messagesList = MutableStateFlow<List<Message>>(emptyList())
    val messagesList = _messagesList.asStateFlow()

    init {
        viewModelScope.launch {
            repository.receivedMessage().collectLatest {
                repository.receivedMessage().collectLatest {
                    _messagesList.value = it
                }
            }
        }
    }

    fun sendMessage(text: String) {
        viewModelScope.launch(Dispatchers.Main) {
            repository.sendMessageToChat(text, receiverId)
        }
    }

}