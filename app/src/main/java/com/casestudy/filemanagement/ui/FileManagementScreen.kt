package com.casestudy.filemanagement.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.lazy.items

@Composable
fun FileManagementScreen(viewModel: FileManagementViewModel)
{
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()) {
        it?.let { uri -> viewModel.handleEvent(UiEvent.UploadFile(uri)) }
    }

    LaunchedEffect(true) {
        viewModel.loadFiles()
    }

    if (!uiState.isConsentSubmitted) {
        ConsentDialog(
            content = uiState.consentContent,
            onSubmitConsent = {
            viewModel.handleEvent(UiEvent.SubmitConsent) }
        )
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.Start) {
        Button(
            onClick = { launcher.launch("*/*") },
            enabled = uiState.isConsentSubmitted) {
                Text(text = "Upload file")
            }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = " Your files:",
            fontSize = 16.sp
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            content = {
            items(uiState.files) { file ->
                Text(text = "${file.fileName} (${if (file.uploaded) "Uploaded" else "Pending"})", modifier = Modifier.padding(8.dp))
            }
        })
    }
}
