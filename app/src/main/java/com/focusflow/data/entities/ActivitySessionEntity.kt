package com.focusflow.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activity_sessions")
data class ActivitySessionEntity(
    @PrimaryKey
    val id: String,
    val templateId: String,
    val date: String, // Format "YYYY-MM-DD"
    val actualDurationMinutes: Int?, // Durée réellement complétée
    val status: String, // 'PLANNED', 'ACTIVE', 'COMPLETED', 'INTERRUPTED', 'IGNORED', 'MISSED'
    val interruptionsCount: Int,
    val focusScore: Int?, // 0 à 100
    val createdAt: Long
)
