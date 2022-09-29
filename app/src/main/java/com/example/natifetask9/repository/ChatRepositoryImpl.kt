package com.example.natifetask9.repository

import com.example.natifetask9.model.Message
import com.example.natifetask9.model.User
import com.example.natifetask9.server.TCPClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.withContext

class ChatRepositoryImpl(
    private val tcpClient: TCPClient,
) : ChatRepository {

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

    override fun messages(id: String): Flow<Message> {
        return tcpClient.getMessageForChat().filter {
            it.otherUserId == id
        }
    }
}