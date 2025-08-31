package com.example.littlelemon.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class UserPreferences(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_FIRST_NAME = "first_name"
        private const val KEY_LAST_NAME = "last_name"
        private const val KEY_EMAIL = "email"
        private const val KEY_ONBOARDED = "onboarded"
    }

    fun saveUserInfo(firstName: String, lastName: String, email: String) {
        prefs.edit {
            putString(KEY_FIRST_NAME, firstName)
                .putString(KEY_LAST_NAME, lastName)
                .putString(KEY_EMAIL, email)
                .putString(KEY_ONBOARDED, true.toString())
        }
    }

    fun deleteUserInfo() {
        prefs.edit {
            remove(KEY_FIRST_NAME)
            remove(KEY_LAST_NAME)
            remove(KEY_EMAIL)
            remove(KEY_ONBOARDED)
        }
    }

    fun getFirstName(): String? = prefs.getString(KEY_FIRST_NAME, null)
    fun getLastName(): String? = prefs.getString(KEY_LAST_NAME, null)
    fun getEmail(): String? = prefs.getString(KEY_EMAIL, null)
    fun isOnboarded(): Boolean = prefs.getString(KEY_ONBOARDED, "false").toBoolean()
}
