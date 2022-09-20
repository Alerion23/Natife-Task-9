package com.example.natifetask9.server

import java.io.IOException
import java.net.*

class MessengerClientImpl : MessengerClient {

    private var serverSocket: DatagramSocket? = null
    private var response = 0
    private var serverAddress : InetAddress? = null

    override fun startConnection(port: Int) {
        while (response <= 0) {
            try {
                serverSocket = DatagramSocket()
                serverSocket?.soTimeout = 10000
                val message = ByteArray(1024)
                val packet = DatagramPacket(
                    message, message.size, InetAddress.getByName("255.255.255.255"), port
                )
                serverSocket?.send(packet)
                val quote = ByteArray(1024)
                val responsePacket = DatagramPacket(quote, quote.size)
                serverSocket?.receive(responsePacket)
                response = responsePacket.length
                serverAddress = responsePacket.address
            } catch (e: SocketTimeoutException) {
                e.printStackTrace()
            } catch (e: SocketException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun stop() {
        serverSocket?.close()
    }
}