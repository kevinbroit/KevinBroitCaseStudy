package com.casestudy.filemanagement.ui

import android.net.Uri

sealed class UiEvent {
    data object SubmitConsent : UiEvent()
    data object LoadFiles : UiEvent()
    data class UploadFile(val uri: Uri) : UiEvent()
}