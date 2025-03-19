package com.casestudy.usermanagement.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.casestudy.usermanagement.UiEvent
import com.casestudy.usermanagement.UserManagement
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(private val userManagement: UserManagement) : ViewModel() {
    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn: StateFlow<Boolean?> = _isLoggedIn

    init {
        checkLoginStatus()
    }

    fun handleEvent(event: UiEvent) {
        when (event) {
            is UiEvent.Login -> login()
        }
    }

    private fun checkLoginStatus() {
        viewModelScope.launch {
            _isLoggedIn.update {  userManagement.isUserLoggedIn() }
        }
    }

    private fun login() {
        viewModelScope.launch {
            _isLoggedIn.value = userManagement.login()
        }
    }
}