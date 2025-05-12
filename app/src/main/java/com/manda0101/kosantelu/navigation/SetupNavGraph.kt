package com.manda0101.kosantelu.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.manda0101.kosantelu.ui.screen.AddKosanScreen
import com.manda0101.kosantelu.ui.screen.EditKosanScreen
import com.manda0101.kosantelu.ui.screen.MainScreen
import com.manda0101.kosantelu.ui.screen.MainViewModel

@Composable
fun SetupNavGraph(navController: NavHostController, viewModel: MainViewModel) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            MainScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.AddKosan.route) {
            AddKosanScreen(viewModel = viewModel, navController = navController)
        }

        composable(Screen.EditKosan.route) { backStackEntry ->
            val kosanId = backStackEntry.arguments?.getLong(KEY_ID_KOSAN) ?: 0
            if (kosanId == 0L) {
                Toast.makeText(navController.context, "Kosan tidak ditemukan", Toast.LENGTH_SHORT).show()
                return@composable
            }
            EditKosanScreen(kosanId = kosanId, viewModel = viewModel, navController = navController)
        }
    }
}
