package com.example.customproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.customproject.R
import com.example.customproject.data.Timetable

class ViewDate : AppCompatActivity() {
    private lateinit var TimetableViewModel: timeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_date)
        val list  = findViewById<RecyclerView>(R.id.timeList)
        val adapter = ViewDateAdapter()
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this)
        TimetableViewModel =   ViewModelProvider(this).get(timeViewModel::class.java)
        TimetableViewModel.readAllData.observe(this, Observer {Timetable ->
            adapter.setData(Timetable)

        })

    }
}