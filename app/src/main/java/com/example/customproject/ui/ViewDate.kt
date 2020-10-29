package com.example.customproject.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.customproject.R
import com.example.customproject.data.Timetable
import com.example.customproject.utils.Constants.VIEWING

class ViewDate : AppCompatActivity() {
    companion object{

    }
    private lateinit var TimetableViewModel: timeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_date)
        val list  = findViewById<RecyclerView>(R.id.timeList)
        val cancel = findViewById<Button>(R.id.btn_cancel)
        val adapter = ViewDateAdapter()
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this)
        TimetableViewModel =   ViewModelProvider(this).get(timeViewModel::class.java)
        TimetableViewModel.readAllData.observe(this, Observer {Timetable ->
            adapter.setData(Timetable)

        })
        cancel.setOnClickListener {
            val returnIntent = Intent().apply {
                putExtra(VIEWING, "View complete")
            }
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }

    }
}