package com.manda0101.kosantelu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.manda0101.kosantelu.database.KosanDatabase
import com.manda0101.kosantelu.database.KosanRepository
import com.manda0101.kosantelu.navigation.SetupNavGraph
import com.manda0101.kosantelu.ui.theme.KosanTelUTheme
import com.manda0101.kosantelu.ui.screen.MainViewModel
import com.manda0101.kosantelu.ui.screen.MainViewModelFactory
import android.util.Log
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.LaunchedEffect

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("KosanTeluPrefs", "MainActivity: onCreate called.")

        setContent {
            Log.d("KosanTeluPrefs", "MainActivity: setContent called.")

            val context = LocalContext.current

            val kosanDatabase = KosanDatabase.getInstance(applicationContext)
            val kosanDao = kosanDatabase.kosanDao()
            val recycleBinDao = kosanDatabase.recycleBinDao()
            val repository = remember { KosanRepository(kosanDao, recycleBinDao) }
            val viewModelFactory = remember { MainViewModelFactory(repository) }

            val viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

            LaunchedEffect(viewModel) {
                Log.d("KosanTeluPrefs", "MainActivity: LaunchedEffect for ViewModel initialization.")
                viewModel.initializeThemePreferencesManager(context.applicationContext)
            }

            val darkTheme = false

            KosanTelUTheme(darkTheme = darkTheme) {
                val navController = rememberNavController()
                SetupNavGraph(navController = navController, viewModel = viewModel)
            }
        }
    }
}