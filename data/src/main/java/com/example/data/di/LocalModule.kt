package com.example.data.di

import android.content.Context
import com.example.data.repository.datasource.PrefsImpl
import com.example.domain.repository.datasource.Prefs
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localModule = module {

    single<Prefs> {
        PrefsImpl(prefs = get())
    }

    single {
        androidApplication().getSharedPreferences(PrefsImpl.SHARED_FILE, Context.MODE_PRIVATE)
    }

}