package com.casestudy.filemanagement.data

import com.casestudy.filemanagement.model.AppFile
import com.casestudy.filemanagement.model.MedicalFileType
import java.io.File

fun File.toAppFile(originalFileName: String, fileDescription: String =  "This is a description",  fileType: MedicalFileType = MedicalFileType.OTHER): AppFile {
    return AppFile(
        fileName = originalFileName,
        filePath = this.absolutePath,
        description = fileDescription,
        fileType = fileType
    )
}