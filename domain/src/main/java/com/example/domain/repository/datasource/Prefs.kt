package com.example.domain.repository.datasource

interface Prefs {

    fun setUserName(userName: String)

    fun getUserName(): String?

}