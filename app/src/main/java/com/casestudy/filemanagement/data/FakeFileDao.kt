package com.casestudy.filemanagement.data

import android.util.Log
import com.casestudy.filemanagement.model.AppFile
import com.casestudy.filemanagement.model.MedicalFileType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Mock implementation of a file data access object for demonstration purposes.
 * This class provides a mocked list of files to simulate file storage.
 * In a real-world scenario, this would interact with a database.
 */
class FakeFileDao {

    private companion object {
        private const val TAG = "FakeFileDao"
    }
    /**
     *  Holds the list of mock files.
     */
    private val _files = MutableStateFlow<List<AppFile>>(
        listOf(
            AppFile(
                fileName = "file1.pdf",
                filePath = "/mock/path/file1.pdf",
                description = "",
                fileType = MedicalFileType.PRESCRIPTION
            ),
            AppFile(
                fileName = "file2.pdf",
                filePath = "/mock/path/file2.pdf",
                description = "",
                fileType = MedicalFileType.OTHER
            ),
            AppFile(
                fileName = "file3.pdf",
                filePath = "/mock/path/file3.pdf",
                description = "",
                fileType = MedicalFileType.LABORATORY
            )
        )
    )

    val files: StateFlow<List<AppFile>> = _files.asStateFlow()


    /**
     * Adds a new file to the repository.
     *
     * Note: In a real implementation, this function should store the file in a database.
     *
     * @param file The `AppFile` object to be added.
     */
    fun addFile(file: AppFile) {
        Log.d(TAG, "Adding file to the repository")
        // TODO: this function should call store the file in the database.

        _files.value = _files.value + file
    }
}