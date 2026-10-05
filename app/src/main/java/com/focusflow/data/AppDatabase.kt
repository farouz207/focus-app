package com.focusflow.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.focusflow.data.dao.ActivityDao
import com.focusflow.data.entities.ActivitySessionEntity
import com.focusflow.data.entities.ActivityTemplateEntity

@Database(
    entities = [ActivityTemplateEntity::class, ActivitySessionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun activityDao(): ActivityDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "focusflow_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
