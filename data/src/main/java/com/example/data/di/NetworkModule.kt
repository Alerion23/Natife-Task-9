package com.example.data.di

import com.example.data.server.TCPClientImpl
import com.example.data.server.UDPClientImpl
import com.example.domain.server.TCPClient
import com.example.domain.server.UDPClient
import org.koin.dsl.module

val networkModule = module {

    single<UDPClient> {
        UDPClientImpl()
    }

    single<TCPClient> {
        TCPClientImpl()
    }

}
