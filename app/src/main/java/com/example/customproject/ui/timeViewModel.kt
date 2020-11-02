package com.example.customproject.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.customproject.data.Timetable
import com.example.customproject.database.timeDatabase
import com.example.customproject.database.timetableDao
import com.example.customproject.databaseIntent.timetableRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class timeViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<Timetable>>
    var finished: Int
    var chatbot: Int
    private val repository: timetableRepository
    init {
        val timeDAO = timeDatabase.getDatabase(application).timetableDao()
        repository = timetableRepository(timeDAO)
        readAllData = repository.getTimetable()
        finished = 0
        chatbot = 1
    }

    fun returnChatbot(): Int{
        return chatbot
    }

    fun set_Chatbot(num: Int){
        chatbot = num
    }
    fun reset(){
        finished = 0
    }

    fun returnFinished (): Int{
        return finished
    }

    fun change(){
        finished++
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