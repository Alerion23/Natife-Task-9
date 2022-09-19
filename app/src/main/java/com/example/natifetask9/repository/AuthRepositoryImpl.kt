package com.example.natifetask9.repository

import com.example.natifetask9.server.MessengerClient
import com.example.natifetask9.server.MessengerServer
import com.example.natifetask9.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val server: MessengerServer,
    private val client: MessengerClient
) : AuthRepository {

    override suspend fun connectToServer() {
        withContext(Dispatchers.IO) {
            server.start(Constants.PORT)
            client.startConnection(Constants.SERVER_IP, Constants.PORT)
        }
    }
}