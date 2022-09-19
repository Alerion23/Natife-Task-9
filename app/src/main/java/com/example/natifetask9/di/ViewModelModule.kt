package com.example.natifetask9.di

import com.example.natifetask9.ui.auth.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        AuthViewModel(repository = get())
    }

}