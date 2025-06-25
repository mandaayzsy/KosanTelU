package com.manda0101.kosantelu.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.manda0101.kosantelu.ui.screen.*
import com.manda0101.kosantelu.ui.screen.LoginViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    viewModel: MainViewModel,
    loginViewModel: LoginViewModel
) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            MainScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.AddKosan.route) {
            AddKosanScreen(viewModel = viewModel, navController = navController)
        }

        composable(
            route = Screen.EditKosan.route,
            arguments = listOf(navArgument(KEY_ID_KOSAN) { type = NavType.LongType })
        ) { backStackEntry ->
            val kosanId = backStackEntry.arguments?.getLong(KEY_ID_KOSAN) ?: 0L
            EditKosanScreen(kosanId = kosanId, viewModel = viewModel, navController = navController)
        }
    }
}
