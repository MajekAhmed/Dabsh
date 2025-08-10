package com.majek.dabsh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.material3.*
import androidx.navigation.compose.*
import com.majek.dabsh.ui.*
import com.majek.dabsh.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = GameViewModel()

        setContent {
            MaterialTheme {
                val navController = rememberNavController()

                NavHost(navController, startDestination = "home") {
                    composable("home") { HomeScreen(navController, viewModel) }
                    composable("game") { GameScreen(navController, viewModel) }
                    composable("result") { ResultScreen(navController, viewModel) }
                    composable("settings") { SettingsScreen(navController) }
                    composable("privacy") { PrivacyPolicyScreen(navController) }
                }
            }
        }
    }
}
