package com.casestudy.filemanagement.data

import android.content.Context
import android.net.Uri
import android.util.Log
import com.casestudy.filemanagement.model.AppFile
import kotlinx.coroutines.delay
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Manages file encryption, secure storage, and metadata generation.
 *
 * This class provides functionality to:
 * - Encrypt a file selected by the user.
 * - Securely store the encrypted file in the app’s internal storage.
 * - Generate metadata for the stored encrypted file.
 *
 * @property context The application context used for file operations.
 * @property fileEncryptor The encryption utility responsible for encrypting files.

 */
class EncryptedFileRepository(private val context: Context,
                              private val fileEncryptor: FileEncryptor)
{
    companion object {
        private const val TAG = "EncryptedFileRepository"
    }
    /**
     * Encrypts a user-selected file and stores it securely in local storage.
     *
     * @param fileUri The URI of the file selected by the user.
     * @return [AppFile] containing metadata about the stored encrypted file.
     */
    suspend fun store(fileUri: Uri): AppFile {
        Log.d(TAG, "Starting file encryption and storage")
        delay(1000) // simulates a delay

        val uniqueFilename = generateUniqueFilename()
        Log.d(TAG, "Unique filename generated: $uniqueFilename")

        val encryptedFilePath = getEncryptedFilePath(uniqueFilename)
        Log.d(TAG, "Encrypted file path generated: ${encryptedFilePath.absolutePath}")

        val encryptedFile = fileEncryptor.write(fileUri, encryptedFilePath)
        Log.d(TAG, "Encryption successful")

       return encryptedFile.toAppFile(uniqueFilename)
    }

    /**
     * Returns a File object representing the path where the encrypted file will be stored.
     *
     * @param fileName The name of the file to be stored.
     * @return A File object representing the encrypted file's storage path.
     */
    fun getEncryptedFilePath(fileName: String): File {
        return File(context.filesDir, fileName)
    }

    /**
     * Creates an unique filename by appending a timestamp.
     *
     * @param baseName The base name for the file
     * @return A unique filename in the format "baseName_YYYYMMDD_HHmmss".
     */
    fun generateUniqueFilename(baseName: String = "medical_file"): String {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        return "${baseName}_${timestamp}.enc"
    }
}
