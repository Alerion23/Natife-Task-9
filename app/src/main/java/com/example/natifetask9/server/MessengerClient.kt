package com.example.natifetask9.server

import java.io.IOException
import java.net.*

class MessengerClient {

    private var serverSocket: DatagramSocket? = null

    fun startConnection(port: Int) {
        try {
            serverSocket = DatagramSocket()
            serverSocket?.soTimeout = 10000
            while (true) {
                val message = ByteArray(1024)
                val packet = DatagramPacket(
                    message, message.size, InetAddress.getByName("255.255.255.255"), port
                )
                serverSocket?.send(packet)
                val quote = ByteArray(1024)
                val responsePacket = DatagramPacket(quote, quote.size)
                serverSocket?.receive(responsePacket)
            }
        } catch (e: SocketTimeoutException) {
            e.printStackTrace()
        } catch (e: SocketException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun stop() {
        serverSocket?.close()
    }
}