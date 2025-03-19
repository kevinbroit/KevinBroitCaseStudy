package com.casestudy.usermanagement.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.casestudy.Screen
import com.casestudy.usermanagement.ui.LoginViewModel
import com.casestudy.usermanagement.UiEvent

@Composable
fun LoginScreen(navController: NavHostController,loginViewModel: LoginViewModel)
{
    val isLoggedIn = loginViewModel.isLoggedIn.collectAsStateWithLifecycle()

    LaunchedEffect( isLoggedIn.value) {
        if (isLoggedIn.value == true) {
            navController.navigate(Screen.FileManagement.route)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { loginViewModel.handleEvent(UiEvent.Login) },
            modifier = Modifier
                .padding(16.dp)
                .size(width = 200.dp, height = 60.dp)
        ) {
            Text(text = "Login", fontSize = 18.sp)
        }
    }
}