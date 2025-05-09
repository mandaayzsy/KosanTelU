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
import com.manda0101.kosantelu.ui.viewmodel.MainViewModel
import com.manda0101.kosantelu.ui.viewmodel.MainViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Mendapatkan preferensi tema yang disimpan di DataStore
            val darkTheme = getThemePreference()

            // Menggunakan tema berdasarkan preferensi pengguna
            KosanTelUTheme(darkTheme = darkTheme) {
                // Retrieve instance of the database and DAO
                val kosanDatabase = KosanDatabase.getInstance(applicationContext)
                val kosanDao = kosanDatabase.kosanDao()
                val recycleBinDao = kosanDatabase.recycleBinDao()  // Add this line to get the RecycleBinDao

                // Create the repository using both DAOs
                val repository = KosanRepository(kosanDao, recycleBinDao)

                // Initialize ViewModel with the repository
                val viewModelFactory = MainViewModelFactory(repository)
                val viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

                // Setup Navigation
                val navController = rememberNavController()
                SetupNavGraph(navController = navController, viewModel = viewModel)
            }
        }
    }
}
