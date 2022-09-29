package com.example.natifetask9.di

import com.example.natifetask9.repository.AuthRepository
import com.example.natifetask9.repository.AuthRepositoryImpl
import com.example.natifetask9.repository.ChatRepository
import com.example.natifetask9.repository.ChatRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    single<AuthRepository> {
        AuthRepositoryImpl(udpClient = get(), tcpClient = get(), prefs = get())
    }

    single<ChatRepository> {
        ChatRepositoryImpl(tcpClient = get())
    }

}