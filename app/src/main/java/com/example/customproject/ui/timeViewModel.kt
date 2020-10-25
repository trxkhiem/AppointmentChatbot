package com.example.customproject.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.customproject.data.Timetable
import com.example.customproject.database.timeDatabase
import com.example.customproject.database.timetableDao
import com.example.customproject.databaseIntent.timetableRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class timeViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<Timetable>>
    private val repository: timetableRepository
    init {
        val timeDAO = timeDatabase.getDatabase(application).timetableDao()
        repository = timetableRepository(timeDAO)
        readAllData = repository.getTimetable()
    }

    fun addTime(time: Timetable) {
        viewModelScope.launch(Dispatchers.IO){
            repository.addTime(time)
        }
    }

    fun addTimeList(time: List<Timetable>) {
        viewModelScope.launch(Dispatchers.IO){
            repository.addTimeList(time)
        }
    }

    fun getTime(aDay: String, aTime: String) : LiveData<Timetable>{
             return repository.getTime(aDay, aTime)

    }

    fun updateTime(time: Timetable) {
        viewModelScope.launch(Dispatchers.IO){
            repository.updateTime(time)
        }
    }
}