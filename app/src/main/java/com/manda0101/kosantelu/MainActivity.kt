package com.manda0101.kosantelu

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.manda0101.kosantelu.database.KosanDatabase
import com.manda0101.kosantelu.database.KosanRepository
import com.manda0101.kosantelu.database.UserRepository
import com.manda0101.kosantelu.model.User
import com.manda0101.kosantelu.navigation.SetupNavGraph
import com.manda0101.kosantelu.ui.screen.LoginViewModel
import com.manda0101.kosantelu.ui.screen.LoginViewModelFactory
import com.manda0101.kosantelu.ui.screen.MainViewModel
import com.manda0101.kosantelu.ui.screen.MainViewModelFactory
import com.manda0101.kosantelu.ui.theme.KosanTelUTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("KosanTeluPrefs", "MainActivity: onCreate called.")

        setContent {
            val context = LocalContext.current

            val kosanDatabase = KosanDatabase.getInstance(applicationContext)
            val kosanDao = kosanDatabase.kosanDao()
            val recycleBinDao = kosanDatabase.recycleBinDao()
            val userDao = kosanDatabase.userDao()

            val kosanRepository = remember { KosanRepository(kosanDao, recycleBinDao) }
            val userRepository = remember { UserRepository(userDao) }

            val mainViewModelFactory = remember { MainViewModelFactory(kosanRepository) }
            val loginViewModelFactory = remember { LoginViewModelFactory(userRepository) }

            val mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
            val loginViewModel = ViewModelProvider(this, loginViewModelFactory)[LoginViewModel::class.java]

            // Tambahkan user dummy (admin@mail.com / 1234) jika belum ada
            LaunchedEffect(Unit) {
                val user = userDao.getUserByEmailAndPassword("admin@mail.com", "1234")
                if (user == null) {
                    Log.d("DummyUser", "Menambahkan user dummy")
                    GlobalScope.launch(Dispatchers.IO) {
                        userDao.insert(
                            User(
                                id = 0,
                                name = "Admin",
                                email = "admin@mail.com",
                                password = "1234",
                                noHp = "08123456789"
                            )
                        )
                    }
                } else {
                    Log.d("DummyUser", "User dummy sudah ada")
                }
            }

            LaunchedEffect(mainViewModel) {
                mainViewModel.initializeThemePreferencesManager(context.applicationContext)
            }

            val darkTheme = false

            KosanTelUTheme(darkTheme = darkTheme) {
                SetStatusBarColor()
                val navController = rememberNavController()
                SetupNavGraph(
                    navController = navController,
                    viewModel = mainViewModel,
                    loginViewModel = loginViewModel
                )
            }
        }
    }
}

@Composable
fun SetStatusBarColor() {
    val systemUiController = rememberSystemUiController()
    val statusBarColor = Color(0xFF34AAFF)

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = false
        )
    }
}
