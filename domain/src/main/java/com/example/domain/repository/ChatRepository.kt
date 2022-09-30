package com.example.domain.repository

import com.example.domain.model.Message
import com.example.domain.model.User
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun getUsers()

    fun userList(): Flow<List<User>>

    suspend fun sendMessageToChat(text: String, receiverId: String)

    fun messages(id: String): Flow<Message>

}