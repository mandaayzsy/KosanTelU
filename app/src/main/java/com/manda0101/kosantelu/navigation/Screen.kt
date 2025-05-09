package com.manda0101.kosantelu.navigation

sealed class Screen(val route: String) {
    // Route untuk layar utama yang menampilkan daftar kosan
    data object Home : Screen("mainScreen")

    // Route untuk layar detail kosan (tanpa ID)
    data object FormBaru : Screen("detailScreen")

    // Route untuk layar detail kosan berdasarkan ID
    data object FormUbah : Screen("detailScreen/{$KEY_ID_KOSAN}") {
        // Fungsi untuk membentuk route dengan ID kosan
        fun withId(id: Long) = "detailScreen/$id"
    }
}

// Key untuk argument ID kosan
const val KEY_ID_KOSAN = "kosanId"
