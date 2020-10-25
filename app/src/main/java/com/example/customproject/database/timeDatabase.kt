package com.example.customproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.customproject.data.Timetable

@Database(entities = [Timetable::class], version = 1)
abstract class timeDatabase: RoomDatabase() {
    abstract fun timetableDao(): timetableDao
    companion object {
        @Volatile
        private var INSTANCE: timeDatabase? = null

        fun getDatabase(context: Context): timeDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    timeDatabase::class.java,
                    "time_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}