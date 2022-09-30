package com.example.natifetask9.repository

import com.example.natifetask9.data.Prefs
import com.example.natifetask9.server.TCPClient
import com.example.natifetask9.server.UDPClient
import com.example.natifetask9.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val udpClient: UDPClient,
    private val tcpClient: TCPClient,
    private val prefs: Prefs
) : AuthRepository {

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