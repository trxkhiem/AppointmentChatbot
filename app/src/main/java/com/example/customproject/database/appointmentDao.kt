package com.example.customproject.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.customproject.data.Appointment
import com.example.customproject.data.Timetable
import java.util.*

@Dao
interface appointmentDao {
    @Query("SELECT * FROM Appointment WHERE email =:userEmail AND phone = :phone")
    fun getAppointment(userEmail: String, phone: String): LiveData<Appointment>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAppointment(appointment: Appointment)

    @Update
    fun updateAppointment(appointment: Appointment)

    @Delete
    fun deleteAppointment(appointment: Appointment)

}