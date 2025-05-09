package com.manda0101.kosantelu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.manda0101.kosantelu.database.KosanDatabase
import com.manda0101.kosantelu.database.KosanRepository
import com.manda0101.kosantelu.ui.theme.KosanTelUTheme
import com.manda0101.kosantelu.ui.viewmodel.MainViewModel
import com.manda0101.kosantelu.ui.viewmodel.MainViewModelFactory
import com.manda0101.kosantelu.navigation.SetupNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KosanTelUTheme {
                val kosanDatabase = KosanDatabase.getInstance(applicationContext)
                val kosanDao = kosanDatabase.kosanDao()
                val repository = KosanRepository(kosanDao)
                val viewModelFactory = MainViewModelFactory(repository)
                val viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

                val navController = rememberNavController()
                SetupNavGraph(navController = navController, viewModel = viewModel)
            }
        }
    }
}
