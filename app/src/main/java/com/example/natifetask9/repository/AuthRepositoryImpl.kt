package com.example.natifetask9.repository

import com.example.natifetask9.server.MessengerClient
import com.example.natifetask9.utils.Constants

class AuthRepositoryImpl(
    private val client: MessengerClient,
) : AuthRepository {

    override suspend fun connectToServer() {
        client.startConnection(Constants.UDP_PORT)
    }
}