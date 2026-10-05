package com.focusflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.focusflow.ui.screens.DashboardScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Ici nous instancierons le ViewModel pour passer les vraies données
                    DashboardScreen(
                        greeting = "Bonjour",
                        activities = emptyList(), // Remplacé par les flux de Room
                        onNewActivityClick = { /* Naviguer vers SessionPlannerScreen */ },
                        onActivityClick = { /* Naviguer vers SessionPlannerScreen */ }
                    )
                }
            }
        }
    }
}
