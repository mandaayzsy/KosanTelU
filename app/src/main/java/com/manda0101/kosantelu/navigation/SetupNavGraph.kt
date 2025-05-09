package com.manda0101.kosantelu.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.manda0101.kosantelu.ui.screen.AddKosanScreen
import com.manda0101.kosantelu.ui.screen.DetailScreen
import com.manda0101.kosantelu.ui.screen.EditKosanScreen
import com.manda0101.kosantelu.ui.screen.MainScreen
import com.manda0101.kosantelu.ui.screen.RecycleBinScreen
import com.manda0101.kosantelu.ui.viewmodel.MainViewModel

@Composable
fun SetupNavGraph(navController: NavHostController, viewModel: MainViewModel) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            MainScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screen.AddKosan.route) {
            AddKosanScreen(viewModel = viewModel, navController = navController)
        }
        composable(Screen.Detail.route) { backStackEntry ->
            val kosanId = backStackEntry.arguments?.getLong("kosanId") ?: 0
            DetailScreen(navController = navController, kosanId = kosanId)
        }
        composable(Screen.EditKosan.route) { backStackEntry ->
            val kosanId = backStackEntry.arguments?.getLong("kosanId") ?: 0
            EditKosanScreen(kosanId = kosanId, viewModel = viewModel, navController = navController)
        }

        // Add this route for the RecycleBinScreen
        composable("recycleBin") {
            RecycleBinScreen(viewModel = viewModel)  // Pass viewModel here
        }
    }
}