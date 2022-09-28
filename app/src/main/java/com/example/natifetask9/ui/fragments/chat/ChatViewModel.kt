package com.example.natifetask9.ui.fragments.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.natifetask9.model.Message
import com.example.natifetask9.repository.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ChatViewModel(
    private val repository: ChatRepository,
    private val receiverId: String
) : ViewModel() {

    private val _messagesList = MutableSharedFlow<List<Message>>(
        replay = 0
    )
    val messagesList = _messagesList.asSharedFlow()

    private var currentList = emptyList<Message>()

    init {
        viewModelScope.launch {
            repository.messages(receiverId).collectLatest {
                val newList = currentList + it
                currentList = newList
                _messagesList.emit(newList)
            }
        }
        currentList = emptyList()
    }

    fun sendMessage(text: String) {
        viewModelScope.launch(Dispatchers.Main) {
            repository.sendMessageToChat(text, receiverId)
        }
    }

}