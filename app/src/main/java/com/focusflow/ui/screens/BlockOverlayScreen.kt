package com.focusflow.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BlockOverlayScreen(
    blockedAppName: String,
    onEmergencyUnlock: () -> Unit
) {
    // Cet écran sera rendu à l'intérieur d'un System Alert Window ou d'une Activity translucide
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xE6111C2D)), // Fond semi-transparent sombre
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .background(Color.White, RoundedCornerShape(24.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Icône de bouclier
            Surface(
                shape = RoundedCornerShape(50),
                color = Color(0xFFFFDAD6),
                modifier = Modifier.size(64.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("🛡️", fontSize = 32.sp)
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Application bloquée",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111C2D)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "$blockedAppName vous distrait. Votre séance de focus est actuellement en cours.",
                fontSize = 14.sp,
                color = Color(0xFF434654),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onEmergencyUnlock,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0041C5)),
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Text("Retourner au Focus")
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            TextButton(
                onClick = { /* Lancer Joker de 60s si permis */ }
            ) {
                Text("Utiliser un Joker (60s)", color = Color(0xFF434654))
            }
        }
    }
}
