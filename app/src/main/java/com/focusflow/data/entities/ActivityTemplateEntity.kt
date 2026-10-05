package com.focusflow.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activity_templates")
data class ActivityTemplateEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val category: String, // 'sport', 'etudes', 'travail', 'routine'
    val startTime: String, // Format "HH:mm"
    val durationMinutes: Int,
    val recurringDays: String, // CSV format: "MON,TUE,WED"
    val strictMode: Boolean,
    val useAlarm: Boolean,
    val createdAt: Long
)
