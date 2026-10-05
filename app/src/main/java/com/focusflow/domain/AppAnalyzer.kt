package com.focusflow.domain

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

object AppAnalyzer {
    // Mots-clés pour repérer les distractions (réseaux sociaux, jeux courants)
    private val DISTRACTION_KEYWORDS = listOf(
        "facebook", "instagram", "tiktok", "twitter", "snapchat", "reddit", 
        "youtube", "netflix", "twitch", "pinterest", "discord", "game", "clash"
    )
    
    // Liste des applications d'urgence et essentielles toujours autorisées
    val ESSENTIAL_PACKAGES = listOf(
        "com.android.dialer", 
        "com.android.server.telecom", // Appels
        "com.android.mms", 
        "com.google.android.apps.messaging", // SMS
        "com.android.settings", // Paramètres (pour désactiver le service en cas de bug)
        "com.android.vending" // Play Store
    )

    fun getInstalledApps(context: Context): List<AppInfo> {
        val pm = context.packageManager
        val apps = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        
        return apps.mapNotNull { appInfo ->
            // On ne garde que les applications qui ont une interface (lançables)
            if (pm.getLaunchIntentForPackage(appInfo.packageName) != null) {
                val appName = pm.getApplicationLabel(appInfo).toString()
                val isSystem = (appInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0
                val isDistraction = isDistraction(appInfo.packageName, appName)
                
                AppInfo(
                    packageName = appInfo.packageName,
                    appName = appName,
                    isSystem = isSystem,
                    isEssential = ESSENTIAL_PACKAGES.contains(appInfo.packageName),
                    isSuggestedDistraction = isDistraction && !ESSENTIAL_PACKAGES.contains(appInfo.packageName)
                )
            } else null
        }
    }

    private fun isDistraction(packageName: String, appName: String): Boolean {
        val lowerPackage = packageName.lowercase()
        val lowerName = appName.lowercase()
        return DISTRACTION_KEYWORDS.any { lowerPackage.contains(it) || lowerName.contains(it) }
    }
}

data class AppInfo(
    val packageName: String,
    val appName: String,
    val isSystem: Boolean,
    val isEssential: Boolean,
    val isSuggestedDistraction: Boolean
)
