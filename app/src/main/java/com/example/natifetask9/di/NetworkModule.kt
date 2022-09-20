package com.example.natifetask9.di

import com.example.natifetask9.server.MessengerClient
import com.example.natifetask9.server.MessengerClientImpl
import org.koin.dsl.module

val networkModule = module {

    factory<MessengerClient> {
        MessengerClientImpl()
    }

}
