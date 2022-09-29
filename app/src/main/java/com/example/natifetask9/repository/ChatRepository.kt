package com.example.natifetask9.repository

import com.example.natifetask9.model.Message
import com.example.natifetask9.model.User
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun getUsers()

    fun userList(): Flow<List<User>>

    suspend fun sendMessageToChat(text: String, receiverId: String)

    fun messages(id: String): Flow<Message>

}