package com.example.natifetask9.di

import android.content.Context
import android.content.SharedPreferences
import com.example.natifetask9.data.PrefsImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localModule = module {

    single {
        PrefsImpl(prefs = get())
    }

    single {
        androidApplication().getSharedPreferences(PrefsImpl.SHARED_FILE, Context.MODE_PRIVATE)
    }

}