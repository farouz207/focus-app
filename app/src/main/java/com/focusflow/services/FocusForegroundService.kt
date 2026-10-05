package com.focusflow.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

/**
 * Service au premier plan (Foreground Service).
 * Indispensable pour éviter que l'OS ne tue l'application pendant une session de focus de 2 heures.
 */
class FocusForegroundService : Service() {

    private val CHANNEL_ID = "FocusFlowSessionChannel"

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val activityId = intent?.getStringExtra("ACTIVITY_ID")
        
        // Notification persistante obligatoire pour un Foreground Service
        val notification = createNotification("Session de focus active", "Ne lâchez rien !")
        startForeground(1, notification)

        // TODO: Mettre à jour l'état dans Room/DataStore pour dire que la session a démarré.
        // C'est ce qui activera le AppBlockerService.

        // START_STICKY indique à l'OS de relancer le service s'il manque de mémoire
        return START_STICKY
    }

    private fun createNotification(title: String, content: String): Notification {
        // Rediriger vers l'écran actif quand on clique sur la notification
        // val pendingIntent = PendingIntent.getActivity(...)
        
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // TODO: Mettre le logo FocusFlow
            .setOngoing(true) // Impossible à effacer (swiper)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Session Focus",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
