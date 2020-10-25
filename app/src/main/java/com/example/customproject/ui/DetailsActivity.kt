package com.example.customproject.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.customproject.R
import com.example.customproject.data.Appointment
import com.example.customproject.data.Timetable
import kotlinx.android.synthetic.main.activity_appointment_form.*

class DetailsActivity : AppCompatActivity() {
    companion object{
        const val UPDATE = "Updating"
    }
    lateinit var appoint : Appointment
    private lateinit var appointmentVM: appointmentViewModel
    private lateinit var timeVM: timeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        appoint = intent.getParcelableExtra<Appointment>(verifyActivity.VERIFY)!!
        appointmentVM = ViewModelProvider(this).get(appointmentViewModel::class.java)
        timeVM = ViewModelProvider(this).get(timeViewModel::class.java)
        val update = findViewById<Button>(R.id.btn_update)
        val cancel  = findViewById<Button>(R.id.btn_cancel)
        val delete  = findViewById<Button>(R.id.btn_delete)
        val userName = findViewById<EditText>(R.id.userName)
        val userReason = findViewById<EditText>(R.id.userReason)
        val userEmail = findViewById<EditText>(R.id.userEmail)
        val userPhone = findViewById<EditText>(R.id.userPhone)
        val time = findViewById<TextView>(R.id.timeInput)
        val day = findViewById<TextView>(R.id.dayInput)

        userName.setText(appoint.fName)
        userEmail.setText(appoint.email)
        userReason.setText (appoint.reason)
        userPhone.setText(appoint.phone)
        time.setText(appoint.time)
        day.setText(appoint.day)
        cancel.setOnClickListener {
            val returnIntent = Intent().apply {
                putExtra(UPDATE, "update incomplete")
            }
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }

        update.setOnClickListener{
            if (!checkFields(userName.text.toString(), userEmail.text.toString(), userPhone.text.toString(),
                    userReason.text.toString(), day.text.toString(), time.text.toString())){
                Toast.makeText(this, "Please make sure all the information are entered ", Toast.LENGTH_LONG).show()
            } else {
                val updateAppointment = Appointment(appoint.appointmentID, userName.text.toString(), userEmail.text.toString(), userPhone.text.toString(),
                    userReason.text.toString(), day.text.toString(), time.text.toString())
                appointmentVM.updateAppointment(updateAppointment)
                val returnIntent = Intent().apply {
                    putExtra(UPDATE, "update complete")
                }
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        }

        delete.setOnClickListener {
            timeVM.getTime(appoint.day, appoint.time).observe(this, Observer {
                var appointTime = it
                val updatetime = Timetable(appointTime.id, appointTime.day, appointTime.time, 0)
                timeVM.updateTime(updatetime)
            })
            appointmentVM.deleteAppointment(appoint)
            val returnIntent = Intent().apply {
                putExtra(UPDATE, "delete complete")
            }
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }

    private fun checkFields (name: String, email: String,  phone: String, reason: String, day: String, time: String) : Boolean{
        return (name != "" && email != "" && phone != "" && reason !="" && day != "" && time !="")
    }
    override fun onBackPressed() {
        val returnIntent = Intent().apply {
            putExtra(UPDATE, "update incomplete")
        }
        setResult(Activity.RESULT_OK, returnIntent)
        super.onBackPressed()
    }
}