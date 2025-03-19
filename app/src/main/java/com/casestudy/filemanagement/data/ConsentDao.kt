package com.casestudy.filemanagement.data

import android.util.Log
import kotlinx.coroutines.delay

/**
 * Mock implementation of a consent service  for demonstration purposes.
 */
class ConsentDao {
    private companion object {
        private const val TAG = "ConsentService"
    }
    suspend fun get(): String {
        Log.d(TAG, "Getting the consent content")

        delay(1000)
        return "This is the consent content. Please read it carefully."
    }

    suspend fun write(): Boolean {
        Log.d(TAG, "Writing the user's consent")

        delay(1000)
        return true
    }
}