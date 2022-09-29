package com.example.natifetask9.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun connectToServer(userName: String)

    fun authState() : Flow<Boolean>

    fun getUserName() : String?

}