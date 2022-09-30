package com.example.data.di

import com.example.data.repository.AuthRepositoryImpl
import com.example.data.repository.ChatRepositoryImpl
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.ChatRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<AuthRepository> {
        AuthRepositoryImpl(udpClient = get(), tcpClient = get(), prefs = get())
    }

    single<ChatRepository> {
        ChatRepositoryImpl(tcpClient = get())
    }

}