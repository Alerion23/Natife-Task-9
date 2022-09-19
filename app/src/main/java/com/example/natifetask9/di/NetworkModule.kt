package com.example.natifetask9.di

import com.example.natifetask9.server.MessengerClient
import com.example.natifetask9.server.MessengerServer
import org.koin.dsl.module

val networkModule = module {

    single {
        MessengerServer()
    }

    single {
        MessengerClient()
    }

}
