package com.manda0101.kosantelu.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.manda0101.kosantelu.ui.screen.DetailScreen
import com.manda0101.kosantelu.ui.screen.MainScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = "mainScreen"
    ) {
        composable("mainScreen") {
            MainScreen(navController)
        }
        composable(
            "detailScreen/{kosanId}",
            arguments = listOf(navArgument("kosanId") { type = NavType.LongType })
        ) { backStackEntry ->
            val kosanId = backStackEntry.arguments?.getLong("kosanId")
            if (kosanId != null) {
                DetailScreen(navController, kosanId)
            }
        }
    }
}