package com.manda0101.kosantelu.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.manda0101.kosantelu.ui.screen.MainScreen
import com.manda0101.kosantelu.ui.screen.AddKosanScreen
import com.manda0101.kosantelu.ui.screen.DetailScreen
import com.manda0101.kosantelu.ui.viewmodel.MainViewModel

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController(), viewModel: MainViewModel) {
    NavHost(
        navController = navController,
        startDestination = "mainScreen"
    ) {
        composable("mainScreen") {
            MainScreen(navController, viewModel)
        }
        composable("addKosan") {
            AddKosanScreen(viewModel, navController)
        }
        composable("detailScreen/{kosanId}") { backStackEntry ->
            val kosanId = backStackEntry.arguments?.getLong("kosanId") ?: 0
            DetailScreen(navController, kosanId)
        }
    }
}
