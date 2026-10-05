package com.focusflow.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.focusflow.data.entities.ActivityTemplateEntity
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionPlannerScreen(
    initialActivity: ActivityTemplateEntity?,
    onSave: (ActivityTemplateEntity) -> Unit,
    onDelete: (String) -> Unit,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf(initialActivity?.title ?: "Entraînement") }
    var duration by remember { mutableStateOf(initialActivity?.durationMinutes?.toString() ?: "60") }
    var isStrictMode by remember { mutableStateOf(initialActivity?.strictMode ?: true) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (initialActivity == null) "Nouvelle activité" else "Éditer l'activité", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Retour")
                    }
                },
                actions = {
                    if (initialActivity != null) {
                        IconButton(onClick = { onDelete(initialActivity.id) }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Supprimer", tint = Color(0xFFBA1A1A))
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF9F9FF))
            )
        },
        containerColor = Color(0xFFF9F9FF),
        bottomBar = {
            Box(modifier = Modifier.padding(16.dp)) {
                Button(
                    onClick = {
                        val newActivity = ActivityTemplateEntity(
                            id = initialActivity?.id ?: UUID.randomUUID().toString(),
                            title = title,
                            category = "sport", // Simplifié pour l'exemple
                            startTime = "17:00",
                            durationMinutes = duration.toIntOrNull() ?: 60,
                            recurringDays = "MON,WED,FRI",
                            strictMode = isStrictMode,
                            useAlarm = true,
                            createdAt = System.currentTimeMillis()
                        )
                        onSave(newActivity)
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0041C5))
                ) {
                    Icon(Icons.Filled.Check, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (initialActivity == null) "Enregistrer" else "Mettre à jour")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Nom de l'activité") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                shape = RoundedCornerShape(12.dp)
            )
            
            OutlinedTextField(
                value = duration,
                onValueChange = { duration = it },
                label = { Text("Durée (minutes)") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                shape = RoundedCornerShape(12.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Mode Strict", fontWeight = FontWeight.Bold, color = Color(0xFF111C2D))
                    Text("Bloque totalement les distractions", fontSize = 12.sp, color = Color(0xFF434654))
                }
                Switch(
                    checked = isStrictMode,
                    onCheckedChange = { isStrictMode = it },
                    colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF0041C5), checkedTrackColor = Color(0xFFDCE1FF))
                )
            }
        }
    }
}
