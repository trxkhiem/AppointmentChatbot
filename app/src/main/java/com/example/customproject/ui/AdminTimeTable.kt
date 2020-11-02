package com.example.customproject.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.customproject.R
import com.example.customproject.data.Timetable
import com.example.customproject.utils.Constants
import com.example.customproject.utils.Constants.LOGGING

class AdminTimeTable : AppCompatActivity() {
    private lateinit var TimetableViewModel: timeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_time_table)
        val day = findViewById<EditText>(R.id.dayy)
        val time = findViewById<EditText>(R.id.timee)
        val isavailable = findViewById<EditText>(R.id.isAvailable)
        val update = findViewById<Button>(R.id.submit)
        val cancel  = findViewById<Button>(R.id.cancel)
        TimetableViewModel = ViewModelProvider(this).get(timeViewModel::class.java)
        update.setOnClickListener {
            TimetableViewModel.getTime(day.text.toString(), time.text.toString()).observe(this, Observer {
                val result = it
                var isActive = 0
                if (result != null){
                    if (isavailable.text.toString().toLowerCase() == "yes"){
                        isActive = 0
                        val newtime = Timetable(result.id, result.day, result.time, isActive)
                        TimetableViewModel.updateTime(newtime)
                        val returnIntent = Intent().apply {putExtra(LOGGING, "complete")}
                        setResult(Activity.RESULT_OK, returnIntent)
                        finish()
                    } else if(isavailable.text.toString().toLowerCase() == "no") {
                        isActive = 1
                        val newtime = Timetable(result.id, result.day, result.time, isActive)
                        TimetableViewModel.updateTime(newtime)
                        val returnIntent = Intent().apply {putExtra(LOGGING, "complete")}
                        setResult(Activity.RESULT_OK, returnIntent)
                        finish()
                    } else {
                        Toast.makeText(this, "Please enter yes or no into the available field", Toast.LENGTH_LONG).show()
                    }
                }
                else {
                    Toast.makeText(this, "The day and time you have entered are invalid. Please check again!", Toast.LENGTH_LONG).show()
                }
            })
        }
        cancel.setOnClickListener {
            val returnIntent = Intent().apply {
                putExtra(Constants.VERIFY, "verify incomplete")
            }
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }
}