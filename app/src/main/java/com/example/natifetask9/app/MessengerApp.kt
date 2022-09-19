package com.example.natifetask9.app

import android.app.Application
import com.example.natifetask9.di.networkModule
import com.example.natifetask9.di.repositoryModule
import com.example.natifetask9.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MessengerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(listOf(viewModelModule, repositoryModule, networkModule))
        }
    }
}