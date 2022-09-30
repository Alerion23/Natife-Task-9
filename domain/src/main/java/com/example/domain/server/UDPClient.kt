package com.example.domain.server

import java.net.InetAddress

interface UDPClient {

    fun getIpAddress(port: Int) : InetAddress?

}