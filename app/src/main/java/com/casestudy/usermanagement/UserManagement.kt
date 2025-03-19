package com.casestudy.usermanagement

import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class UserManagement() {
    fun isUserLoggedIn(): Boolean {
           return false
    }

    suspend fun login(): Boolean {
        delay(1000.milliseconds)
        return true
    }
}