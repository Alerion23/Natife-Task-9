package com.example.natifetask9.repository

import com.example.natifetask9.model.Message
import com.example.natifetask9.model.User
import com.example.natifetask9.server.TCPClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext

class ChatRepositoryImpl(
    private val tcpClient: TCPClient,
) : ChatRepository {

    private var messageList = MutableStateFlow<List<Message>>(emptyList())
    private var filteredList = MutableStateFlow<List<Message>>(emptyList())

    override suspend fun getUsers() {
        withContext(Dispatchers.IO) {
            tcpClient.sendGetUsers()
        }
    }

    override fun userList(): Flow<List<User>> {
        return tcpClient.getUsers()
    }

    override suspend fun sendMessageToChat(text: String, receiverId: String) {
        withContext(Dispatchers.IO) {
            tcpClient.sendMessageForChat(text, receiverId)
        }
    }

    override suspend fun startListenMessages() {
        withContext(Dispatchers.IO) {
            tcpClient.getMessageForChat().collectLatest {
                val currentList = messageList.value
                messageList.value = currentList + it
            }
        }
    }

    override suspend fun startFilterMessages(id: String) {
        withContext(Dispatchers.IO) {
            messageList.collectLatest {
                filteredList.value = it.filter { message ->
                    message.otherUserId.contains(id)
                }
            }
        }
    }

    override fun messages(): Flow<List<Message>> {
        return filteredList
    }
}