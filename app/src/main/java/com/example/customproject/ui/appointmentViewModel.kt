package com.example.customproject.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.customproject.data.Appointment
import com.example.customproject.data.Timetable
import com.example.customproject.database.appointmentDao
import com.example.customproject.database.appointmentDatabase
import com.example.customproject.database.timeDatabase
import com.example.customproject.database.timetableDao
import com.example.customproject.databaseIntent.appointmentRepository
import com.example.customproject.databaseIntent.timetableRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class appointmentViewModel(application: Application): AndroidViewModel(application) {
    //val readAllData: LiveData<List<Timetable>>
    private val repository: appointmentRepository

    init {
        val appointmentDAO = appointmentDatabase.getDatabase(application).appointmentDao()
        repository = appointmentRepository(appointmentDAO)
        //readAllData = repository.
    }

    fun addAppointment(appoint: Appointment) {
        viewModelScope.launch(Dispatchers.IO){
            repository.addAppointment(appoint)
        }
    }

    fun getAppointment(email: String, phone: String) : LiveData<Appointment>{
        return repository.getAppointment(email, phone)
    }

    fun updateAppointment(appoint: Appointment) {
        viewModelScope.launch(Dispatchers.IO){
            repository.updateAppointment(appoint)
        }
    }

    fun deleteAppointment(appoint: Appointment) {
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAppointment(appoint)
        }
    }
}