package com.casestudy

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.casestudy.filemanagement.ui.FileManagementScreen
import com.casestudy.filemanagement.ui.FileManagementViewModel
import com.casestudy.usermanagement.ui.LoginScreen
import com.casestudy.usermanagement.ui.LoginViewModel

sealed class Screen(val route: String) {
    data object Loading : Screen("loading")
    data object FileManagement : Screen("file_management")
    data object Login : Screen("login")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    fileManagementViewModel: FileManagementViewModel,
    loginViewModel: LoginViewModel
) {
    NavHost(
        navController = navController,
        startDestination  = Screen.Login.route
    ) {
        composable(route = Screen.Loading.route) {
            LoadingScreen(navController,loginViewModel)
        }
        composable(route = Screen.Login.route) {
            LoginScreen(navController,loginViewModel)
        }
        composable(route = Screen.FileManagement.route) {
            FileManagementScreen(fileManagementViewModel)
        }
    }
}