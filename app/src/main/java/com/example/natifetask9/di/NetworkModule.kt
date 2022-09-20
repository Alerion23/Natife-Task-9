package com.example.natifetask9.di

import com.example.natifetask9.server.MessengerClient
import org.koin.dsl.module

val networkModule = module {

    single {
        MessengerClient()
    }

    single {
        MessengerClient()
    }

}
