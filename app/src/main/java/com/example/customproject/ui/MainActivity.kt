package com.example.customproject.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customproject.R
import com.example.customproject.data.Message
import com.example.customproject.data.Timetable
import com.example.customproject.data.timeList.timelists
import com.example.customproject.utils.BotResponse
import com.example.customproject.utils.Constants.ADD_APPOINTMENT
import com.example.customproject.utils.Constants.OPEN_GOOGLE
import com.example.customproject.utils.Constants.OPEN_SEARCH
import com.example.customproject.utils.Constants.RECEIVE_ID
import com.example.customproject.utils.Constants.SEND_ID
import com.example.customproject.utils.Constants.UPDATE_APPOINTMENT
import com.example.customproject.utils.Constants.VIEW_APPOINTMENT
import com.example.customproject.utils.Constants.VIEW_TIMETABLE
import com.example.customproject.utils.Time
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var TimetableViewModel: timeViewModel
    var msgList = mutableListOf<Message>()
    private val TAG = "MainActivity"
    private lateinit var adt: MessagingAdapter
    companion object{
        const val ADD = "Add Appointment"
        const val VERIFY = "Verifying"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView()
        clickEvents()
        TimetableViewModel = ViewModelProvider(this).get(timeViewModel::class.java)
        TimetableViewModel.addTimeList(timelists)
        customMessage("Hello! Today you are speaking with Lichscaa, how may I help you?")
    }


    private fun clickEvents() {
        btn_send.setOnClickListener {
            sendMsg()
        }

        //Scroll back to correct position when user clicks on text view
        et_message.setOnClickListener {
            GlobalScope.launch {
                delay(100)
                withContext(Dispatchers.Main) {
                    rv_messages.scrollToPosition(adt.itemCount - 1)

                }
            }
        }
    }

    private fun recyclerView() {
        adt = MessagingAdapter()
        rv_messages.adapter = adt
        rv_messages.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun customMessage(s: String) {
        GlobalScope.launch {
            delay(1000)
            launch(Dispatchers.Main){
                val timeStamp = Time.timing()
                msgList.add(Message(s, RECEIVE_ID, timeStamp))
                adt.addMessage(Message(s, RECEIVE_ID, timeStamp))
                rv_messages.scrollToPosition(adt.itemCount - 1)
            }

        }
    }

    private fun sendMsg() {
        val message = et_message.text.toString()
        val timeStamp = Time.timing()

        if (message.isNotEmpty()) {
            //Adds it to our local list
            msgList.add(Message(message, SEND_ID, timeStamp))
            et_message.setText("")

            adt.addMessage(Message(message, SEND_ID, timeStamp))
            rv_messages.scrollToPosition(adt.itemCount - 1)
            botResponse(message)
        }
    }

    override fun onStart() {
        super.onStart()
        //In case there are messages, scroll to bottom when re-opening app
        GlobalScope.launch {
            delay(100)
            withContext(Dispatchers.Main) {
                rv_messages.scrollToPosition(adt.itemCount - 1)
            }
        }
    }

    private fun botResponse(message: String) {
        val timeS = Time.timing()
        GlobalScope.launch {
            //Fake response delay
            delay(1000)

            launch(Dispatchers.Main) {
                //Gets the response
                val res = BotResponse.basicResponses(message)
                //Adds it to our local list
                msgList.add(Message(res, RECEIVE_ID, timeS))
                //Inserts our message into the adapter
                adt.addMessage(Message(res, RECEIVE_ID, timeS))
                //Scrolls us to the position of the latest message
                rv_messages.scrollToPosition(adt.itemCount - 1)

                //Starts Google
                when (res) {
                    OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String? = message.substringAfterLast("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }

                    ADD_APPOINTMENT-> {
                        val intent = Intent(this@MainActivity, AppointmentFormActivity::class.java)
                        startActivityForResult(intent, 1)
                    }

                    VIEW_APPOINTMENT->{
                        val intent = Intent(this@MainActivity, verifyActivity::class.java)
                        startActivityForResult(intent, 2)
                    }

                    UPDATE_APPOINTMENT->{
                        val intent = Intent(this@MainActivity, verifyActivity::class.java)
                        startActivityForResult(intent, 2)
                    }

                    VIEW_TIMETABLE->{
                        val intent = Intent(this@MainActivity, ViewDate::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        if (requestCode == 1){
            val msg =data?.getStringExtra(ADD)
            if (msg != null) {
                botResponse(msg)
            }
        }
        if (requestCode == 2){
            val msg =data?.getStringExtra(VERIFY)
            if (msg != null) {
                botResponse(msg)
            }
        }
    }
}
