package com.example.littlelemon.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.littlelemon.data.local.UserPreferences

class UserViewModel : ViewModel() {
    private var userPrefs: UserPreferences? = null

    var firstName by mutableStateOf<String?>(null)
        private set

    var lastName by mutableStateOf<String?>(null)
        private set

    var email by mutableStateOf<String?>(null)
        private set

    var isOnboarded by mutableStateOf(false)
        private set

    var isLoading by mutableStateOf(true)
        private set

    fun init(context: Context) {
        if (userPrefs == null) {
            userPrefs = UserPreferences(context.applicationContext)
            loadUserInfo()
        }
    }

    private fun loadUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            userPrefs?.let {
                firstName = it.getFirstName()
                lastName = it.getLastName()
                email = it.getEmail()
                isOnboarded = it.isOnboarded()
                isLoading = false
            }
        }
    }

    fun saveUserInfo(firstName: String, lastName: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userPrefs?.saveUserInfo(firstName, lastName, email)
            loadUserInfo()
        }
    }

    fun deleteUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            userPrefs?.deleteUserInfo()
            loadUserInfo()
        }
    }
}
