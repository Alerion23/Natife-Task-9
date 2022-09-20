package com.example.natifetask9.server

import java.net.InetAddress

interface TCPClient {

    fun startConnection(ip: InetAddress, port: Int, userName: String)

    fun sendMessage(message: String): String?

    fun stop()

}