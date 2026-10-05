package com.focusflow.services

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.focusflow.domain.AppAnalyzer
import kotlinx.coroutines.*

/**
 * Service d'accessibilité qui écoute les changements de fenêtres (ouverture d'applis).
 * C'est la méthode la plus rapide et fiable pour bloquer une application instantanément.
 */
class AppBlockerService : AccessibilityService() {

    // Simule l'accès à la base de données Room/DataStore
    private var isSessionActive = false
    private var userBlockedPackages = setOf<String>()

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d("FocusFlow", "Service d'accessibilité connecté.")
        // TODO: Observer l'état de la session depuis Room/Flow
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val packageName = event.packageName?.toString() ?: return
            
            // 1. Si c'est une application d'urgence, on ne bloque JAMAIS.
            if (AppAnalyzer.ESSENTIAL_PACKAGES.contains(packageName)) {
                return
            }

            // 2. Si une session est active et que l'app est dans la liste de blocage
            if (isSessionActive && userBlockedPackages.contains(packageName)) {
                // 3. Forcer le retour à l'accueil (Bouton Home)
                performGlobalAction(GLOBAL_ACTION_HOME)

                // 4. Lancer notre activité d'écran de blocage par-dessus
                showBlockScreen(packageName)
            }
        }
    }

    private fun showBlockScreen(blockedPackage: String) {
        // Redirige vers notre écran Compose de blocage (System Alert Window ou Activity)
        /*
        val intent = Intent(this, BlockOverlayActivity::class.java).apply {
            putExtra("BLOCKED_APP", blockedPackage)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        startActivity(intent)
        */
        Log.d("FocusFlow", "Application bloquée : $blockedPackage")
    }

    override fun onInterrupt() {
        Log.w("FocusFlow", "Service d'accessibilité interrompu.")
    }
}
