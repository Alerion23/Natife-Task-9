package com.example.natifetask9.server

import com.example.natifetask9.model.BaseDto
import com.example.natifetask9.model.ConnectDto
import com.example.natifetask9.model.ConnectedDto
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.lang.Exception
import java.net.InetAddress
import java.net.Socket

class TCPClientImpl : TCPClient {

    private var socket: Socket? = null
    private var writer: PrintWriter? = null
    private var reader: BufferedReader? = null
    private val isConnected = MutableStateFlow(false)

    override fun startConnection(ip: InetAddress, port: Int, userName: String) {
        var response: String?
        socket = Socket(ip, port)
        writer = PrintWriter(OutputStreamWriter(socket?.getOutputStream()))
        reader = BufferedReader(InputStreamReader(socket?.getInputStream()))
        isConnected.tryEmit(true)
        val connection = isConnected.value
        val gson = Gson()
        while (connection) {
            try {
                response = reader?.readLine()
                if (response != null) {
                    val responseModel = gson.fromJson(response, BaseDto::class.java)
                    when (responseModel.action) {
                        BaseDto.Action.CONNECTED -> {
                            val connectedModel =
                                Gson().fromJson(responseModel.payload, ConnectedDto::class.java)
                            val userId = connectedModel.id
                            val jsonString = gson.toJson(ConnectDto(userId, userName))
                            val finalJsonString =
                                gson.toJson(BaseDto(BaseDto.Action.CONNECT, jsonString))
                            sendMessage(finalJsonString)
                        }
                        BaseDto.Action.USERS_RECEIVED -> {}
                        BaseDto.Action.NEW_MESSAGE -> {}
                        else -> {}
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun sendMessage(message: String) {
        writer?.println(message)
        writer?.flush()
    }

    override fun stop() {
        socket?.close()
        writer?.close()
        reader?.close()
        isConnected.tryEmit(false)
    }
}