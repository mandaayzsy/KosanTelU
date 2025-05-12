package com.manda0101.kosantelu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.manda0101.kosantelu.database.KosanDatabase
import com.manda0101.kosantelu.database.KosanRepository
import com.manda0101.kosantelu.datapreferences.getThemePreference
import com.manda0101.kosantelu.navigation.SetupNavGraph
import com.manda0101.kosantelu.ui.theme.KosanTelUTheme
import com.manda0101.kosantelu.ui.screen.MainViewModel
import com.manda0101.kosantelu.ui.viewmodel.MainViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val darkTheme = getThemePreference()
            KosanTelUTheme(darkTheme = darkTheme) {

                val kosanDatabase = KosanDatabase.getInstance(applicationContext)
                val kosanDao = kosanDatabase.kosanDao()
                val recycleBinDao = kosanDatabase.recycleBinDao()
                val repository = KosanRepository(kosanDao, recycleBinDao)
                val viewModelFactory = MainViewModelFactory(repository)
                val viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
                val navController = rememberNavController()
                SetupNavGraph(navController = navController, viewModel = viewModel)
            }
        }
    }
}
