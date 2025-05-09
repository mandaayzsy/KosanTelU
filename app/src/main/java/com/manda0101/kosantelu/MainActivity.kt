package com.manda0101.kosantelu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.manda0101.kosantelu.navigation.SetupNavGraph // Pastikan SetupNavGraph diimpor
import com.manda0101.kosantelu.ui.theme.KosanTelUTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KosanTelUTheme {
                // Menggunakan SetupNavGraph dengan navController
                val navController = rememberNavController()
                SetupNavGraph(navController = navController)  // Gantilah MainScreen dengan SetupNavGraph
            }
        }
    }
}
