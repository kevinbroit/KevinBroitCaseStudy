package com.casestudy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.casestudy.filemanagement.data.ConsentDao
import com.casestudy.filemanagement.ui.FileManagementViewModel
import com.casestudy.filemanagement.data.FileEncryptor
import com.casestudy.filemanagement.data.FakeFileDao
import com.casestudy.filemanagement.data.EncryptedFileRepository
import com.casestudy.usermanagement.ui.LoginViewModel
import com.casestudy.usermanagement.UserManagement

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavGraph(navController = rememberNavController(),
                FileManagementViewModel(
                    this.application,
                    ConsentDao(),
                    EncryptedFileRepository(context = this, FileEncryptor(this)),
                    FakeFileDao()
                ),
                LoginViewModel(UserManagement())
            )
        }
    }
}

@Composable
fun LoadingScreen(navController: NavHostController, loginViewModel: LoginViewModel) {
    val isLoggedIn = loginViewModel.isLoggedIn.collectAsState()

    LaunchedEffect(isLoggedIn.value) {
        when (isLoggedIn.value) {
            false -> navController.navigate(Screen.Login.route) {
                popUpTo("loading") { inclusive = true } // Remove LoadingScreen from back stack
            }
            true -> navController.navigate(Screen.FileManagement.route) {
                popUpTo("loading") { inclusive = true } // Remove LoadingScreen from back stack
            }
            null -> TODO()
        }
    }


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}