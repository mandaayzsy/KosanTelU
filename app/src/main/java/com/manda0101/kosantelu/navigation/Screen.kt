package com.manda0101.kosantelu.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("mainScreen")
    data object AddKosan : Screen("addKosan")
    data object EditKosan : Screen("editKosan/{kosanId}") {
        fun withId(id: Long) = "editKosan/$id"
    }
    data object Detail : Screen("detailScreen/{kosanId}") {
        fun withId(id: Long) = "detailScreen/$id"
    }
}

// Key untuk argument ID kosan
const val KEY_ID_KOSAN = "kosanId"
