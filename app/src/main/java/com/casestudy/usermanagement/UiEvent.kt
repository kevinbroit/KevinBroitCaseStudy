package com.casestudy.usermanagement

sealed class UiEvent {
    data object Login : UiEvent()
}