package com.casestudy.filemanagement.ui

import com.casestudy.filemanagement.model.AppFile

data class UiState(

    val consentContent: String = "",
    val isLoading: Boolean = false,
    val isConsentSubmitted: Boolean = false,
    val error: String? = null,
    val files: List<AppFile> = emptyList()
)