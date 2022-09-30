package com.example.data.repository

import com.example.data.utils.Constants
import com.example.domain.repository.datasource.Prefs
import com.example.domain.server.TCPClient
import com.example.domain.server.UDPClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

internal class AuthRepositoryImpl(
    private val udpClient: UDPClient,
    private val tcpClient: TCPClient,
    private val prefs: Prefs
) : com.example.domain.repository.AuthRepository {

    override suspend fun connectToServer(userName: String) {
        val ipAddress = udpClient.getIpAddress(Constants.UDP_PORT)
        if (ipAddress != null) {
            tcpClient.startConnection(ipAddress, Constants.TCP_PORT, userName)
        }
        prefs.setUserName(userName)
    }

    override fun authState(): Flow<Boolean> {
        return tcpClient.getAuthStatus()
    }

    override fun getUserName(): String? = prefs.getUserName()

    override suspend fun logOut() {
        withContext(Dispatchers.IO) {
            prefs.setUserName("")
            tcpClient.sendDisconnect()
            tcpClient.disconnect()
        }
    }
}