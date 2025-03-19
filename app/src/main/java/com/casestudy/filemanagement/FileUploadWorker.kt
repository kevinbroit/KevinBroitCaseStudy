package com.casestudy.filemanagement

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Worker responsible for synchronizing files with the backend. This class contains a Stub implementation.

 * The actual implementation should integrate with:
 * - A `FileRepository` to retrieve files.
 * - A network service to upload files.
 * - Error handling and logging mechanisms.
 *
 * @param context The application context.
 * @param workerParams The parameters required for the worker execution.
 */
class FileSynchronizationWorker(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        return synchronizeFiles()
    }

    companion object {
        private const val TAG = "FileSynchronizationWorker"
    }

    /**
     * Handles the actual file synchronization logic.
     *
     * Steps:
     * - Get not-synchronized files from `FileRepository`. (TODO)
     * - Upload those files to the backend. (TODO)
     * - Return success if all files are uploaded.
     * - Return retry if an exception occurs.
     *
     * @return A [Result.success] if the upload is completed, or [Result.retry] if an error occurs.
     */
    private suspend fun synchronizeFiles(): Result {
        return withContext(Dispatchers.IO) {
            try {
                // TODO: perform synchronization here.
                Log.d(TAG, "This functionality shall be implemented in the future. ")

                Result.success()
            } catch (e: Exception) {
                Result.retry()
            }
        }
    }
}
