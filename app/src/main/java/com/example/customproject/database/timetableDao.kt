package com.example.customproject.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.customproject.data.Appointment
import com.example.customproject.data.Timetable
import java.util.*

@Dao
interface timetableDao {

    @Query("SELECT * FROM Timetable")
    fun getAvailableTime(): LiveData<List<Timetable>>

    @Query("SELECT * FROM Timetable WHERE day = :aDay AND time = :aTime ")
    fun getTime(aDay: String, aTime: String): LiveData<Timetable>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addTimetable(time: Timetable)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addTimetableList(time: List<Timetable>)

    @Update
    fun updateTime(tTable: Timetable)
    
}