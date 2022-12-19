package com.example.scooterx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.scooterx.presentation.screens.login_register.view_model.LoginViewModel
import com.example.scooterx.presentation.screens.splash.SplashScreen
import com.example.scooterx.presentation.ui.theme.ScooterXTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScooterXTheme() {
                val systemUiController = rememberSystemUiController()
                val isDark = isSystemInDarkTheme()
                val navigationBackground = Color.Black
                val systemBackground = Color.Black
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = systemBackground,
                        darkIcons = isDark
                    )
                    systemUiController.setNavigationBarColor(
                        color = navigationBackground,
                        darkIcons = !isDark
                    )
                }
                val loginViewModel = viewModel(modelClass = LoginViewModel::class.java)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var showLandingScreen by remember { mutableStateOf(true) }
                    if (showLandingScreen) {
                        SplashScreen(onTimeout = { showLandingScreen = false })
                    } else {
                        Navigation(loginViewModel = loginViewModel)
                    }
                }
            }
        }
    }
}