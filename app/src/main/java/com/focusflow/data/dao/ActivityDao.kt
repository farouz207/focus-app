package com.focusflow.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.focusflow.data.entities.ActivitySessionEntity
import com.focusflow.data.entities.ActivityTemplateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {

    // --- Templates ---
    @Query("SELECT * FROM activity_templates ORDER BY startTime ASC")
    fun getAllTemplates(): Flow<List<ActivityTemplateEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplate(template: ActivityTemplateEntity)

    @Query("DELETE FROM activity_templates WHERE id = :templateId")
    suspend fun deleteTemplate(templateId: String)

    // --- Sessions ---
    @Query("SELECT * FROM activity_sessions ORDER BY date DESC, createdAt DESC")
    fun getAllSessions(): Flow<List<ActivitySessionEntity>>
    
    @Query("SELECT * FROM activity_sessions WHERE date = :date ORDER BY createdAt DESC")
    fun getSessionsForDate(date: String): Flow<List<ActivitySessionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: ActivitySessionEntity)

    @Update
    suspend fun updateSession(session: ActivitySessionEntity)
}
