package com.casestudy.filemanagement

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

/**
* This class is responsible for scheduling the file upload process. Creates a one-time work request,
* ensuring internet availability, and enqueues the work request using WorkManager.
* The file upload is performed asynchronously via `FileSynchronizationWorker`.
*/
class FileUploadScheduler {
    companion object {
        fun scheduleUpload(context: Context) {
            Log.d("FileUploadScheduler", "Scheduling OneTimeWorkRequestBuilder")

            val workRequest = OneTimeWorkRequestBuilder<FileSynchronizationWorker>().setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            ).build()

            WorkManager.getInstance(context).enqueue(workRequest)
        }
    }
}