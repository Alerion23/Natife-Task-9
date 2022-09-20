package com.example.natifetask9.server

import android.util.Log
import com.example.natifetask9.model.BaseDto
import com.example.natifetask9.model.ConnectDto
import com.example.natifetask9.model.ConnectedDto
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.lang.Exception
import java.net.InetAddress
import java.net.Socket

class TCPClientImpl() : TCPClient {

    private var socket: Socket? = null
    private var writer: PrintWriter? = null
    private var reader: BufferedReader? = null
    private var response: String? = null

    override fun startConnection(ip: InetAddress, port: Int, userName: String) {
        val gson = Gson()
        try {
            socket = Socket(ip, port)
            writer = PrintWriter(OutputStreamWriter(socket?.getOutputStream()))
            reader = BufferedReader(InputStreamReader(socket?.getInputStream()))
            while (response != null) {
                response = reader?.readLine()
                val responseModel = gson.fromJson(response, BaseDto::class.java)
                if (responseModel.action == BaseDto.Action.CONNECTED) {
                    val connectedModel =
                        Gson().fromJson(responseModel.payload, ConnectedDto::class.java)
                    val userId = connectedModel.id
                    val jsonString = gson.toJson(ConnectDto(userId, userName))
                    val finalJsonString = gson.toJson(BaseDto(BaseDto.Action.CONNECT, jsonString))
                    writer?.println(finalJsonString)
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun sendMessage(message: String): String? {
        writer?.println(message)
        return reader?.readLine()
    }

    override fun stop() {
        socket?.close()
        writer?.close()
        reader?.close()
    }
}