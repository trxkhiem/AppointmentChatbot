package com.example.customproject.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.customproject.R
import com.example.customproject.data.Appointment
import com.example.customproject.data.Timetable
import kotlinx.android.synthetic.main.activity_appointment_form.*

class AppointmentFormActivity: AppCompatActivity(), AdapterView.OnItemSelectedListener{
    private lateinit var appointmentVM: appointmentViewModel
    private lateinit var timeVM: timeViewModel
    var list_of_days = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
    var list_of_time = arrayOf("9:00", "10:00", "11:00", "12:00", "13:00","14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00")
    private var DayoW ="Monday"
    private var TimeoD ="9:00"
    companion object{
        const val ADD = "Add Appointment"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment_form)
        dayInput!!.setOnItemSelectedListener(this)
        timeInput!!.setOnItemSelectedListener(this)
        appointmentVM = ViewModelProvider(this).get(appointmentViewModel::class.java)
        timeVM = ViewModelProvider(this).get(timeViewModel::class.java)
        // Create an ArrayAdapter using a simple spinner layout and days array
        val dayList = ArrayAdapter(this, android.R.layout.simple_spinner_item, list_of_days)
        val submit = findViewById<Button>(R.id.submit)
        val cancel  = findViewById<Button>(R.id.cancel)
        val userName = findViewById<EditText>(R.id.userName)
        val reason = findViewById<EditText>(R.id.userReason)
        val userEmail = findViewById<EditText>(R.id.userEmail)
        val userPhone = findViewById<EditText>(R.id.userPhone)
        val timeList = ArrayAdapter(this, android.R.layout.simple_spinner_item, list_of_time)
        // Set layout to use when the list of choices appear
        dayList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        timeList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        dayInput!!.setAdapter(dayList)
        timeInput!!.setAdapter(timeList)
        /**
        timeVM.getTime(DayoW, TimeoD).observe(this, Observer {
            appointTime = it
        })
        **/
        submit.setOnClickListener {
            timeVM.getTime(DayoW, TimeoD).observe(this, Observer {
                var appointTime = it
                if (!checkFields(userName.text.toString(), userEmail.text.toString(), userPhone.text.toString(), reason.text.toString(), DayoW, TimeoD)){
                    Toast.makeText(this, "Please ensure all the fields are entered", Toast.LENGTH_LONG).show()
                } else {
                    if (appointTime == null){
                        Toast.makeText(this, "the chosen time is unavailable, please choose another one", Toast.LENGTH_LONG).show()
                    } else{
                        val id = appointTime.id
                        val day = appointTime.day
                        val time =  appointTime.time
                        val timetable = Timetable(id, day, time, 1)
                        if (timetable != null) {
                            timeVM.updateTime(timetable)
                            val appoint = Appointment(0, userName.text.toString(), userEmail.text.toString(), userPhone.text.toString(), reason.text.toString(), DayoW, TimeoD)
                            appointmentVM.addAppointment(appoint)
                            val returnIntent = Intent().apply {
                                putExtra(ADD, "add complete")
                            }
                            setResult(Activity.RESULT_OK, returnIntent)
                            finish()
                        } else {
                            Toast.makeText(this, "Cannot find the chosen available time", Toast.LENGTH_LONG).show()
                        }

                    }

                }
            })
        }

        cancel.setOnClickListener {
            val returnIntent = Intent().apply {
                putExtra(ADD, "add incomplete")
            }
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }

    }


    private fun checkFields (name: String, email: String,  phone: String, reason: String, day: String, time: String) : Boolean{
        return (name != "" && email != "" && phone != "" && reason !="" && day != "" && time !="")
    }
    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        when (arg0.id.toString()){
            R.id.dayInput.toString() ->
                DayoW = list_of_days[position]
            R.id.timeInput.toString() ->
                TimeoD = list_of_time[position]
       }
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }

    override fun onBackPressed() {
        val returnIntent = Intent().apply {
            putExtra(ADD, "add incomplete")
        }
        setResult(Activity.RESULT_OK, returnIntent)
        super.onBackPressed()
    }

}