package com.example.customproject.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.customproject.R
import com.example.customproject.utils.Constants

class AdminAppointment : AppCompatActivity() {
    private lateinit var aVM : appointmentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_appointment)
        val list  = findViewById<RecyclerView>(R.id.appointmentList)
        val cancel = findViewById<Button>(R.id.btn_cancel)
        val adapter = ViewAppointmentAdapter()
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this)
        aVM =   ViewModelProvider(this).get(appointmentViewModel::class.java)
        aVM.readAllData.observe(this, Observer {Appointment ->
            adapter.setData(Appointment)

        })
        cancel.setOnClickListener {
            val returnIntent = Intent().apply {
                putExtra(Constants.VIEWING, "View complete")
            }
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }
}