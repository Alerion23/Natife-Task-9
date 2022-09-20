package com.example.natifetask9.repository

import com.example.natifetask9.server.MessengerClient
import com.example.natifetask9.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val server: MessengerClient,
) : AuthRepository {

    override suspend fun connectToServer() {
        withContext(Dispatchers.IO) {
            server.startConnection(Constants.PORT)
        }
    }
}