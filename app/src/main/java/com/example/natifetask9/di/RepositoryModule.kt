package com.example.natifetask9.di

import com.example.natifetask9.repository.AuthRepository
import com.example.natifetask9.repository.AuthRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    factory<AuthRepository> {
        AuthRepositoryImpl(server = get())
    }

}