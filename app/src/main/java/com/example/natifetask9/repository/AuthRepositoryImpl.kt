package com.example.natifetask9.repository

import com.example.natifetask9.server.TCPClient
import com.example.natifetask9.server.UDPClient
import com.example.natifetask9.utils.Constants
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val udpClient: UDPClient,
    private val tcpClient: TCPClient,
) : AuthRepository {

    override suspend fun connectToServer(userName: String) {
        val ipAddress = udpClient.getIpAddress(Constants.UDP_PORT)
        if (ipAddress != null) {
            tcpClient.startConnection(ipAddress, Constants.TCP_PORT, userName)
        }
    }

    override fun authState(): Flow<Boolean> {
        return tcpClient.getAuthStatus()
    }
}