package com.manda0101.kosantelu.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("mainScreen")

    data object FormBaru : Screen("detailScreen")

    data object FormUbah : Screen("detailScreen/{kosanId}") {
        fun withId(id: Long) = "detailScreen/$id"
    }
}

// Key untuk argument ID kosan
const val KEY_ID_KOSAN = "kosanId"
