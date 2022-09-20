package com.example.natifetask9.server

interface MessengerClient {

    fun startConnection(port: Int)

    fun stop()

}