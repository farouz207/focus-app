package com.focusflow.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Création du DataStore lié au contexte de l'application
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "focusflow_profile")

class ProfileDataStore(private val context: Context) {

    companion object {
        val FIRST_NAME_KEY = stringPreferencesKey("first_name")
        val AGE_KEY = stringPreferencesKey("age")
        val GOALS_KEY = stringPreferencesKey("goals") // Stocké en CSV "sport,etudes"
        val ONBOARDING_COMPLETED_KEY = booleanPreferencesKey("onboarding_completed")
    }

    val firstNameFlow: Flow<String?> = context.dataStore.data.map { it[FIRST_NAME_KEY] }
    val isOnboardingCompletedFlow: Flow<Boolean> = context.dataStore.data.map { it[ONBOARDING_COMPLETED_KEY] ?: false }

    suspend fun saveProfile(firstName: String, age: String, goals: List<String>) {
        context.dataStore.edit { preferences ->
            preferences[FIRST_NAME_KEY] = firstName
            preferences[AGE_KEY] = age
            preferences[GOALS_KEY] = goals.joinToString(",")
            preferences[ONBOARDING_COMPLETED_KEY] = true
        }
    }
}
