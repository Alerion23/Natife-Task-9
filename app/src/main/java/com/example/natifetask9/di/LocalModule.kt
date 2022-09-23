package com.example.natifetask9.di

import com.example.natifetask9.data.Prefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {

    single {
        Prefs(context = androidContext())
    }

}