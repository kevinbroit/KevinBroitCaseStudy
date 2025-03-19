package com.casestudy.filemanagement.ui

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.casestudy.filemanagement.FileUploadScheduler
import com.casestudy.filemanagement.data.ConsentDao
import com.casestudy.filemanagement.data.EncryptedFileRepository
import com.casestudy.filemanagement.data.FakeFileDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * The FileManagementViewModel is responsible for managing medical files and user consents.
 *
 * @param application The application context used for system services.
 * @param consentDao for handling user consents.
 * @param encryptedFileRepository to manage file encryption and local storage.
 * @param fakeFileDao to handle file data persistence and retrieval.
 */
class FileManagementViewModel(application: Application,
                              private val consentDao: ConsentDao,
                              private val encryptedFileRepository: EncryptedFileRepository,
                              val fakeFileDao: FakeFileDao
) : AndroidViewModel(application) {
    /**
     * Application context used for operations that require a global context.
     */
    private fun getContext(): Context = getApplication<Application>().applicationContext

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    /**
     * Initializes the ViewModel by reading the user's consent status.
     */
    init {
        readConsent()
    }

    /**
     * Handles UI events and maps them to ViewModel actions.
     */
    fun handleEvent(event: UiEvent) {
        when (event) {
            is UiEvent.SubmitConsent -> submitConsent()
            is UiEvent.LoadFiles -> loadFiles()
            is UiEvent.UploadFile -> uploadFile(event.uri)
        }
    }

    /**
     * Encrypts and uploads a file, updating the UI state and scheduling synchronization.
     *
     * This function performs the following steps:
     * - Encrypts the file using `FileManager`.
     * - Stores the encrypted file in the repository.
     * - Updates the UI state  to reflect the new file.
     * - Schedules a background sync task** to upload the file.
     * - Handles errors by logging them and updating the UI with an error message.
     *
     * @param uri The `Uri` of the file selected by the user.
     */
    fun uploadFile(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val appFile = encryptedFileRepository.store(uri)
                fakeFileDao.addFile(appFile)

                _uiState.update { it -> it.copy(files = it.files + appFile) }

                FileUploadScheduler.Companion.scheduleUpload(getContext())
            } catch (e: Exception) {
                Log.e("uploadFile", "Error uploading file: ${e.message}", e)

                _uiState.update { it -> it.copy(error = "Failed to upload file: ${e.message}") }
            }
        }
    }

    /**
     * Reads consent using the `ConsentService` and updates the state with the consent information.
     */
    fun readConsent() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val content = consentDao.get()
                _uiState.update { it.copy(consentContent = content, isLoading = false) }
            } catch (e: Exception) {
                Log.e("readConsent", "Error reading consent: ${e.message}", e)

                _uiState.update { it.copy(error = "Failed to load consent content", isLoading = false) }
            }
        }
    }

    /**
     * Submit consent from user using the `ConsentService` and update the state.
     */
    fun submitConsent() {
        viewModelScope.launch {
            _uiState.update { oldValue -> oldValue.copy(isLoading = true, error = null) }
            try {
                val result = consentDao.write()
                _uiState.update { oldValue -> oldValue.copy(isConsentSubmitted = result, isLoading = false) }

            } catch (e: Exception) {
                Log.e("submitConsent", "Error submitting consent: ${e.message}", e)
                _uiState.update {oldValue ->  oldValue.copy(error = "Failed to write consent content", isLoading = false) }
            }
        }
    }

    /**
     * Loads medical files from the repository and updates the state.
     */
    fun loadFiles() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(files = fakeFileDao.files.value ) }
        }
    }
}