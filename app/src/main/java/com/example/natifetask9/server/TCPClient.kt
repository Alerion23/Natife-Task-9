package com.example.natifetask9.server

import com.example.natifetask9.model.Message
import com.example.natifetask9.model.User
import kotlinx.coroutines.flow.Flow
import java.net.InetAddress

interface TCPClient {

    fun startConnection(ip: InetAddress, port: Int, userName: String)

    fun disconnect()

    fun sendGetUsers()

    fun getUsers(): Flow<List<User>>

    fun getAuthStatus(): Flow<Boolean>

    fun sendMessageForChat(text: String, receiverId: String)

    fun getMessageForChat(): Flow<Message>

    fun sendDisconnect()

}