package com.casestudy.filemanagement.data

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKey
import java.io.File
import java.io.InputStream

/**
 * Handles file encryption using Android's EncryptedFile API.
 *
 * This class is responsible for:
 * - Managing encryption keys securely using the Android Keystore.
 * - Encrypting files before storing them locally.
 * - Ensuring that files remain protected even if the device is compromised.
 *
 * @property context The application context required for accessing the Android Keystore and content resolver.
 */
class FileEncryptor(private val context: Context) {

    companion object {
        private const val TAG = "FileEncryptor"
    }
    /**
     * Creates a secure encryption key using the Android Keystore.
     *
     * The key:
     * - Uses AES-256-GCM for strong encryption.
     * - Is automatically stored in Android Keystore, ensuring security.
     * - Is lazily initialized to improve performance.
     */
    private val masterKey: MasterKey by lazy {
        Log.d(TAG, "Initializing MasterKey for encryption.")
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    /**
     * Write a file and securely stores it.
     *
     * The function:
     * 1. Reads the original file content from the provided Uri.
     * 2. Encrypts the content using AES-256-GCM.
     * 3. Writes the encrypted data to the specified destination file.
     *
     * @param fileUri The URI of the file to be encrypted.
     * @param encryptedFile The destination file where encrypted data will be stored.
     * @return The encrypted file that has been securely stored.
     * @throws Exception If the file cannot be read or encrypted.
     */
    fun write(fileUri: Uri, encryptedFile: File): File {
        Log.d(TAG, "Starting encryption for file: $fileUri")

        return try {
            val encryptedFileInstance = EncryptedFile.Builder(
                context,
                encryptedFile,
                masterKey,
                EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
            ).build()

            // read the original file
            val inputStream: InputStream? = context.contentResolver.openInputStream(fileUri)
            val fileData = inputStream?.readBytes() ?: throw Exception("Unable to read file")

            Log.d(TAG, "Successfully read file: ${fileUri.lastPathSegment}, size: ${fileData.size} bytes")

            encryptedFileInstance.openFileOutput().use { output ->
                output.write(fileData)
            }

            Log.d(TAG, "Encryption successful. File saved at: ${encryptedFile.absolutePath}")

            encryptedFile

        }catch (e: Exception){
            Log.e(TAG, "Error encrypting file: ${e.message}", e)
            throw e
        }
    }
}
