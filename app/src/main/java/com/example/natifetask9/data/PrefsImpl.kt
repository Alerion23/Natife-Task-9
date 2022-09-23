package com.example.natifetask9.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PrefsImpl(
    private val prefs: SharedPreferences
) : Prefs {

    override fun setUserName(userName: String) {
        prefs.edit {
            putString(USER_NAME, userName)
        }
    }

    override fun getUserName(): String? {
        return prefs.getString(USER_NAME, "")
    }

    companion object {
        const val SHARED_FILE = "chat_prefs"
        const val USER_NAME = "user_name"
    }
}