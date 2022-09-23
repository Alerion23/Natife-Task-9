package com.example.natifetask9.di

import com.example.natifetask9.ui.fragments.auth.AuthViewModel
import com.example.natifetask9.ui.fragments.users.UserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        AuthViewModel(repository = get())
    }

    viewModel {
        UserListViewModel(repository = get())
    }

}