package com.focusflow.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.focusflow.domain.AppAnalyzer
import com.focusflow.domain.AppInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSelectionScreen(
    installedApps: List<AppInfo>,
    blockedPackages: Set<String>,
    onToggleApp: (String, Boolean) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Applications bloquées", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    // TODO: Remplacer par un IconButton de retour
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF9F9FF))
            )
        },
        containerColor = Color(0xFFF9F9FF)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Sélectionnez les applications qui vous distraient. Elles seront bloquées par l'écran de focus pendant vos séances.",
                fontSize = 14.sp,
                color = Color(0xFF434654),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Séparer les suggestions des autres
            val suggestedApps = installedApps.filter { it.isSuggestedDistraction }
            val otherApps = installedApps.filter { !it.isSuggestedDistraction && !it.isEssential }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                
                item {
                    Text("Distractions suggérées", fontWeight = FontWeight.Bold, color = Color(0xFFBA1A1A), modifier = Modifier.padding(top = 8.dp))
                }

                items(suggestedApps) { app ->
                    AppItemRow(
                        app = app,
                        isBlocked = blockedPackages.contains(app.packageName) || app.isSuggestedDistraction, // Par défaut bloqué si suggéré
                        onToggle = onToggleApp
                    )
                }

                item {
                    Text("Autres applications", fontWeight = FontWeight.Bold, color = Color(0xFF111C2D), modifier = Modifier.padding(top = 16.dp))
                }

                items(otherApps) { app ->
                    AppItemRow(
                        app = app,
                        isBlocked = blockedPackages.contains(app.packageName),
                        onToggle = onToggleApp
                    )
                }
            }
        }
    }
}

@Composable
fun AppItemRow(app: AppInfo, isBlocked: Boolean, onToggle: (String, Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(app.appName, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF111C2D))
            Text(app.packageName, fontSize = 12.sp, color = Color(0xFF747686))
        }
        Switch(
            checked = isBlocked,
            onCheckedChange = { checked -> onToggle(app.packageName, checked) },
            colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFFBA1A1A), checkedTrackColor = Color(0xFFFFDAD6))
        )
    }
}
