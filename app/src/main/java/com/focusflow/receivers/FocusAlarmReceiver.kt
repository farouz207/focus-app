package com.focusflow.receivers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.focusflow.services.FocusForegroundService

/**
 * Gère le déclenchement exact des sessions de Focus.
 * Survie au redémarrage grâce à ACTION_BOOT_COMPLETED.
 */
class FocusAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED,
            "android.intent.action.QUICKBOOT_POWERON" -> {
                Log.d("FocusAlarm", "Redémarrage détecté. Reprogrammation des alarmes...")
                // TODO: Lire la DB Room en Coroutine et appeler scheduleAlarm pour chaque activité
            }
            else -> {
                val activityId = intent.getStringExtra("ACTIVITY_ID")
                Log.d("FocusAlarm", "Alarme déclenchée pour l'activité : $activityId")
                
                // Lancer le service au premier plan pour maintenir la session
                val serviceIntent = Intent(context, FocusForegroundService::class.java).apply {
                    putExtra("ACTIVITY_ID", activityId)
                }
                
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    context.startForegroundService(serviceIntent)
                } else {
                    context.startService(serviceIntent)
                }
            }
        }
    }

    companion object {
        fun scheduleAlarm(context: Context, activityId: String, triggerAtMillis: Long) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, FocusAlarmReceiver::class.java).apply {
                putExtra("ACTIVITY_ID", activityId)
            }
            val pendingIntent = PendingIntent.getBroadcast(
                context, 
                activityId.hashCode(), // ID unique
                intent, 
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // Utilisation de setExactAndAllowWhileIdle pour contourner le mode Doze
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
                } else {
                    // Demander la permission SCHEDULE_EXACT_ALARM via l'UI
                }
            } else {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
            }
        }
    }
}
