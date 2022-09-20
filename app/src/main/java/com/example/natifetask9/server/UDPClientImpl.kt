package com.example.natifetask9.server

import java.io.IOException
import java.net.*

class UDPClientImpl : UDPClient {

    private var serverSocket: DatagramSocket? = null
    private var serverAddress: InetAddress? = null

    override fun start(port: Int) {
        while (serverAddress != null) {
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

    override fun getIpAddress(): InetAddress? {
        return serverAddress
    }

    override fun stop() {
        serverSocket?.close()
    }
}