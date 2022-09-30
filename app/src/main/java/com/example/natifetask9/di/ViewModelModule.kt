package com.example.natifetask9.di

import com.example.natifetask9.ui.MessengerActivityViewModel
import com.example.natifetask9.ui.fragments.auth.AuthViewModel
import com.example.natifetask9.ui.fragments.chat.ChatViewModel
import com.example.natifetask9.ui.fragments.users.UserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        AuthViewModel(repository = get())
    }

    viewModel {
        UserListViewModel(chatRepository = get(), authRepository = get())
    }

    viewModel {
        ChatViewModel(repository = get(), receiverId = get())
    }

    viewModel {
        MessengerActivityViewModel(repository = get())
    }

}