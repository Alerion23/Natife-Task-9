package com.example.domain.repository

import kotlinx.coroutines.flow.Flow


interface AuthRepository {

    suspend fun connectToServer(userName: String)

    fun authState(): Flow<Boolean>

    fun getUserName(): String?

    suspend fun logOut()

}