package com.example.natifetask9.data

import android.content.Context
import androidx.core.content.edit

class Prefs(
    context: Context
) {

    private val prefs = context.getSharedPreferences(SHARED_FILE, Context.MODE_PRIVATE)

    fun setUserId(id: String) {
        prefs.edit {
            putString(USER_NAME, id)
        }
    }

    fun getUserName(): String? {
        return prefs.getString(USER_NAME, "")
    }

    companion object {
        private const val SHARED_FILE = "chat_prefs"
        private const val USER_NAME = "user_name"
    }
}