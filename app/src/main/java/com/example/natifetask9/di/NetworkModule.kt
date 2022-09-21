package com.example.natifetask9.di

import com.example.natifetask9.server.TCPClient
import com.example.natifetask9.server.TCPClientImpl
import com.example.natifetask9.server.UDPClient
import com.example.natifetask9.server.UDPClientImpl
import org.koin.core.module.dsl.setupInstance
import org.koin.dsl.module

val networkModule = module {

    factory<UDPClient> {
        UDPClientImpl()
    }

    factory<TCPClient> {
        TCPClientImpl()
    }

}
