package com.example.natifetask9.server

import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.SocketException

class MessengerServer {

    private var serverSocket: DatagramSocket? = null

    fun start(port: Int) {
        try {
            serverSocket = DatagramSocket(port)
            val message = ByteArray(1024)
            val packet = DatagramPacket(
                message, message.size, InetAddress.getByName("255.255.255.255"), port
            )
            serverSocket?.receive(packet)
        } catch (e: SocketException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun stop() {
        serverSocket?.close()
    }

    fun getIpAddress(): String {
        return serverSocket?.inetAddress.toString()
    }
}