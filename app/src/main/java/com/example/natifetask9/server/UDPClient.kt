package com.example.natifetask9.server

import java.net.InetAddress

interface UDPClient {

    fun start(port: Int)

    fun getIpAddress(): InetAddress?

    fun stop()

}