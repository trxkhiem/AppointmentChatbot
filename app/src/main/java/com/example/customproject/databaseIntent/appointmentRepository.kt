package com.example.customproject.databaseIntent

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.customproject.data.Appointment
import com.example.customproject.data.Timetable
import com.example.customproject.database.appointmentDao
import com.example.customproject.database.appointmentDatabase
import com.example.customproject.database.timetableDao
import java.util.*

private const val DATABASE_NAME = "appointment-database"
class appointmentRepository(private val appointmentdao: appointmentDao) {

    fun addAppointment(appointment: Appointment){
        appointmentdao.addAppointment(appointment)
    }

    fun updateAppointment(appointment: Appointment){
        appointmentdao.updateAppointment(appointment)
    }

    fun deleteAppointment(appointment: Appointment){
        appointmentdao.deleteAppointment(appointment)
    }

    fun getAppointment(email: String, phone: String): LiveData<Appointment> =  appointmentdao.getAppointment(email, phone)
}