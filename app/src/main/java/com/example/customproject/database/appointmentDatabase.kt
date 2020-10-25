package com.example.customproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.customproject.data.Appointment


@Database(entities = [Appointment::class], version = 1)
abstract class appointmentDatabase : RoomDatabase() {
    abstract fun appointmentDao(): appointmentDao
    companion object {
        @Volatile
        private var INSTANCE: appointmentDatabase? = null

        fun getDatabase(context: Context): appointmentDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    appointmentDatabase::class.java,
                    "appointment_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}