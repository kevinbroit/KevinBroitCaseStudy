package com.casestudy.filemanagement.model

import java.util.UUID

data class AppFile(
    val id: UUID = UUID.randomUUID(),
    val fileName: String,
    val filePath: String,
    val description: String? = null,
    val fileType: MedicalFileType,
    val uploaded: Boolean = false
)

