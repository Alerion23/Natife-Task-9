package com.example.natifetask9.server

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.Socket

class MessengerClient {

    private var clientSocket: Socket? = null
    private var reader: BufferedReader? = null
    private var writer: PrintWriter? = null

    fun startConnection(ip: String, port: Int) {
        clientSocket = Socket(ip, port)
        reader = BufferedReader(InputStreamReader(clientSocket?.getInputStream()))
        writer = PrintWriter(OutputStreamWriter(clientSocket?.getOutputStream()))
    }

    fun sendMessage(msg: String): String? {
        writer?.println(msg)
        return reader?.readLine()
    }

    fun stopConnection() {
        reader?.close()
        writer?.close()
        clientSocket?.close()
    }

}