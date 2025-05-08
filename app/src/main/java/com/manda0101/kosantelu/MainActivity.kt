package com.manda0101.kosantelu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.manda0101.kosantelu.ui.screen.MainScreen
import com.manda0101.kosantelu.ui.theme.KosanTelUTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KosanTelUTheme  {
                MainScreen()
            }
        }
    }
}