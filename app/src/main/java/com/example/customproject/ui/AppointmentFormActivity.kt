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
import com.example.customproject.utils.Constants.ADD
import kotlinx.android.synthetic.main.activity_appointment_form.*
import kotlin.properties.Delegates

class AppointmentFormActivity: AppCompatActivity(), AdapterView.OnItemSelectedListener{
    private lateinit var appointmentVM: appointmentViewModel
    private lateinit var timeVM: timeViewModel
    //declare arrays for days and time
    var list_of_days = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
    var list_of_time = arrayOf("9:00", "10:00", "11:00", "12:00", "13:00","14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00")
    private var DayoW ="Monday"
    private var TimeoD ="9:00"
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment_form)
        dayInput!!.setOnItemSelectedListener(this)
        timeInput!!.setOnItemSelectedListener(this)
        //initialise the view model class
        appointmentVM = ViewModelProvider(this).get(appointmentViewModel::class.java)
        timeVM = ViewModelProvider(this).get(timeViewModel::class.java)
        // Create an ArrayAdapter using a simple spinner layout and days array
        val dayList = ArrayAdapter(this, android.R.layout.simple_spinner_item, list_of_days)
        val timeList = ArrayAdapter(this, android.R.layout.simple_spinner_item, list_of_time)
        val submit = findViewById<Button>(R.id.submit)
        val cancel  = findViewById<Button>(R.id.cancel)
        val userName = findViewById<EditText>(R.id.userName)
        val reason = findViewById<EditText>(R.id.userReason)
        val userEmail = findViewById<EditText>(R.id.userEmail)
        val userPhone = findViewById<EditText>(R.id.userPhone)
        // Set layout to use when the list of choices appear
        dayList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        timeList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        dayInput!!.setAdapter(dayList)
        timeInput!!.setAdapter(timeList)
        submit.setOnClickListener {
            //reset is used to control the live data
            timeVM.reset()
            if (userPhone.text.length < 7) {
                Toast.makeText(this, "Please enter valid phone number", Toast.LENGTH_LONG).show()
            }
            if (!validateEmail(userEmail.text.toString())) {
                Toast.makeText(applicationContext, "Invalid email address", Toast.LENGTH_LONG).show()
            }
            if (!checkFields(userName.text.toString(), userEmail.text.toString(), userPhone.text.toString(), reason.text.toString(), DayoW, TimeoD)) {
                Toast.makeText(this, "Please ensure all the fields are entered", Toast.LENGTH_LONG).show()
            }
            if (validateEmail(userEmail.text.toString()) && userPhone.text.length >= 7) {
                timeVM.getTime(DayoW, TimeoD).observe(this, Observer {
                    var appointTime = it
                    appointmentVM.getAppointment(userEmail.text.toString(), userPhone.text.toString()).observe(this, Observer {
                        var checkAppoint = it
                        //return finished is a variable that is used to control the observer of live data
                        if (timeVM.returnFinished() == 0){
                            //check whether the register session is available
                            if (appointTime == null || appointTime.isAvailable == 1) {
                                Toast.makeText(this, "the chosen time is unavailable, please choose another one", Toast.LENGTH_LONG).show()
                            // check if the email and phone are already used
                            } else if (checkAppoint != null) {
                                Toast.makeText(this, "The email and phone number has been used already. Please try the different one", Toast.LENGTH_LONG).show()
                            } else{
                                val id = appointTime.id
                                val day = appointTime.day
                                val time = appointTime.time
                                val timetable = Timetable(id, day, time, 1)
                                if (timetable != null) {
                                    timeVM.change()
                                    //make the register session time no longer available
                                    timeVM.updateTime(timetable)
                                    val appoint = Appointment(0, userName.text.toString(), userEmail.text.toString(), userPhone.text.toString(), reason.text.toString(), DayoW, TimeoD)
                                    appointmentVM.addAppointment(appoint)
                                    val returnIntent = Intent().apply {putExtra(ADD, "add complete")}
                                    setResult(Activity.RESULT_OK, returnIntent)
                                    finish()
                                } else {
                                    Toast.makeText(this, "timetable is not available", Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    })
                })
            }
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

    private fun validateEmail(email: String): Boolean {
        var check = false
        if (email.matches(emailPattern.toRegex())) {
            check = true
        }
        return check
    }
    override fun onBackPressed() {
        val returnIntent = Intent().apply {
            putExtra(ADD, "add incomplete")
        }
        setResult(Activity.RESULT_OK, returnIntent)
        super.onBackPressed()
    }
}