package com.focusflow.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.focusflow.data.entities.ActivityTemplateEntity

@Composable
fun DashboardScreen(
    greeting: String,
    activities: List<ActivityTemplateEntity>,
    onNewActivityClick: () -> Unit,
    onActivityClick: (ActivityTemplateEntity) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onNewActivityClick,
                containerColor = Color(0xFF0041C5),
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Nouvelle activité")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Nouvelle activité", fontWeight = FontWeight.Bold)
            }
        },
        containerColor = Color(0xFFF9F9FF)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // En-tête
            Text(
                text = greeting,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111C2D),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            
            Text(
                text = "${activities.size} session(s) prévue(s) aujourd'hui.",
                fontSize = 14.sp,
                color = Color(0xFF434654),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // Jauge de progrès
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F3FF)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Objectif quotidien", fontWeight = FontWeight.Bold, color = Color(0xFF111C2D))
                    LinearProgressIndicator(
                        progress = { 0.76f },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).height(8.dp).clip(CircleShape),
                        color = Color(0xFF0041C5),
                        trackColor = Color(0xFFD8E3FB)
                    )
                    Text("76% complété", fontSize = 12.sp, color = Color(0xFF0041C5), fontWeight = FontWeight.Bold)
                }
            }

            Text("Parcours du jour", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111C2D), modifier = Modifier.padding(bottom = 8.dp))

            // Liste des activités
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(activities) { activity ->
                    ActivityItem(activity = activity, onClick = { onActivityClick(activity) })
                }
            }
        }
    }
}

@Composable
fun ActivityItem(activity: ActivityTemplateEntity, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icone
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color(0xFFDEE8FF), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.PlayArrow, contentDescription = null, tint = Color(0xFF0041C5))
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "${activity.startTime} - ${activity.durationMinutes} min",
                fontSize = 12.sp,
                color = Color(0xFF0041C5),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = activity.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111C2D)
            )
            Text(
                text = activity.category.uppercase(),
                fontSize = 10.sp,
                color = Color(0xFF434654),
                modifier = Modifier
                    .padding(top = 4.dp)
                    .background(Color(0xFFF0F3FF), RoundedCornerShape(4.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }
    }
}
