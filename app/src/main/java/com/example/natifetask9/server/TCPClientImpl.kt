package com.example.natifetask9.server

import com.example.natifetask9.model.BaseDto
import com.example.natifetask9.model.ConnectDto
import com.example.natifetask9.model.ConnectedDto
import com.example.natifetask9.model.PingDto
import com.google.gson.Gson
import kotlinx.coroutines.*
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
    private var pingPongJob: Job? = null
    private val isConnected = MutableStateFlow(false)
    private val gson = Gson()

    @DelicateCoroutinesApi
    override fun startConnection(ip: InetAddress, port: Int, userName: String) {
        GlobalScope.launch(Dispatchers.IO) {
            var userId: String? = null
            var response: String?
            socket = Socket(ip, port)
            writer = PrintWriter(OutputStreamWriter(socket?.getOutputStream()))
            reader = BufferedReader(InputStreamReader(socket?.getInputStream()))
            isConnected.tryEmit(true)
            while (isConnected.value) {
                try {
                    response = reader?.readLine()
                    if (response != null) {
                        val responseModel = gson.fromJson(response, BaseDto::class.java)
                        when (responseModel.action) {
                            BaseDto.Action.CONNECTED -> {
                                val connectedModel =
                                    Gson().fromJson(
                                        responseModel.payload,
                                        ConnectedDto::class.java
                                    )
                                userId = connectedModel.id
                                val connectionString = gson.toJson(ConnectDto(userId, userName))
                                val connectionJsonString =
                                    gson.toJson(
                                        BaseDto(
                                            BaseDto.Action.CONNECT,
                                            connectionString
                                        )
                                    )
                                sendMessage(connectionJsonString)
                                pingPongJob = GlobalScope.launch(Dispatchers.IO) {
                                    val pingString = gson.toJson(PingDto(userId))
                                    val pingJsonString =
                                        gson.toJson(BaseDto(BaseDto.Action.PING, pingString))
                                    sendMessage(pingJsonString)
                                    delay(10000)
                                    stop()
                                }
                            }
                            BaseDto.Action.USERS_RECEIVED -> {}
                            BaseDto.Action.NEW_MESSAGE -> {}
                            BaseDto.Action.PONG -> {
                                pingPongJob?.cancel()
                                pingPongJob = GlobalScope.launch(Dispatchers.IO) {
                                    if (userId != null) {
                                        val pingString = gson.toJson(PingDto(userId))
                                        val pingJsonString =
                                            gson.toJson(BaseDto(BaseDto.Action.PING, pingString))
                                        sendMessage(pingJsonString)
                                        delay(10000)
                                        stop()
                                    }
                                }
                            }
                            else -> {}
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
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