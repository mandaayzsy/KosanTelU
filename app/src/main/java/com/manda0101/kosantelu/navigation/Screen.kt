package com.manda0101.kosantelu.navigation

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Home : Screen("mainScreen")
    data object AddKosan : Screen("addKosan")
    data object EditKosan : Screen("editKosan/{kosanId}")
}

const val KEY_ID_KOSAN = "kosanId"