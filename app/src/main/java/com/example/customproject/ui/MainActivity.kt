package com.example.customproject.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customproject.R
import com.example.customproject.data.Message
import com.example.customproject.data.timeList.timelists
import com.example.customproject.utils.BotResponse
import com.example.customproject.utils.Constants.ADD
import com.example.customproject.utils.Constants.ADD_APPOINTMENT
import com.example.customproject.utils.Constants.CANCEL_APPOINTMENT
import com.example.customproject.utils.Constants.LOGGING
import com.example.customproject.utils.Constants.RECEIVE_ID
import com.example.customproject.utils.Constants.SEND_ID
import com.example.customproject.utils.Constants.UPDATE_APPOINTMENT
import com.example.customproject.utils.Constants.VERIFY
import com.example.customproject.utils.Constants.VIEWING
import com.example.customproject.utils.Constants.VIEW_APPOINTMENT
import com.example.customproject.utils.Constants.VIEW_TIMETABLE
import com.example.customproject.utils.Time
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var TimetableViewModel: timeViewModel
    private lateinit var toggle: ActionBarDrawerToggle
    var msgList = mutableListOf<Message>()
    private val TAG = "MainActivity"
    private lateinit var adt: MessagingAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView()
        clickEvents()
        toggle = ActionBarDrawerToggle(this, drawer_layout, R.string.open, R.string.close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        TimetableViewModel = ViewModelProvider(this).get(timeViewModel::class.java)
        TimetableViewModel.addTimeList(timelists)
        customMessage(
            "Hello! Today you are speaking with Lichscaa, I am here to assist you with your appointment process.\n" +
                    "You can either add, delete, update, view an appointment and check available time"
        )
        nav_view.setNavigationItemSelectedListener {
            if (it.itemId == R.id.fItem){
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                intent.putExtra(LOGGING, "viewappointment")
                /**set request code for each intent**/
                startActivityForResult(intent, 4)
            } else {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                intent.putExtra(LOGGING, "edittimetable")
                /**set request code for each intent**/
                startActivityForResult(intent, 4)
            }
            true
        }
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
            // delay message 1 second
            delay(1000)
            //switch it to the main thread and update the UI
            launch(Dispatchers.Main){
                // get the time stamp
                val timeStamp = Time.timing()
                // add the message to the adapter
                msgList.add(Message(s, RECEIVE_ID, timeStamp))
                adt.addMessage(Message(s, RECEIVE_ID, timeStamp))
                rv_messages.scrollToPosition(adt.itemCount - 1)
            }

        }
    }

    private fun sendMsg() {
        //get the value of the message
        val message = et_message.text.toString()
        val timeStamp = Time.timing()
        if (message.isNotEmpty()) {
            //Adds it to our local list
            msgList.add(Message(message, SEND_ID, timeStamp))
            et_message.setText("")
            // add the message to the adaoter
            adt.addMessage(Message(message, SEND_ID, timeStamp))
            rv_messages.scrollToPosition(adt.itemCount - 1)
            // the bot will response base on the send message
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
                // determine the which commands from the user has been used.
                when (res) {
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

                    CANCEL_APPOINTMENT->{
                        val intent = Intent(this@MainActivity, verifyActivity::class.java)
                        startActivityForResult(intent, 2)
                    }

                    VIEW_TIMETABLE->{
                        val intent = Intent(this@MainActivity, ViewDate::class.java)
                        startActivityForResult(intent, 3)
                    }
                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // get the values from the second activity
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        // request code to determine which scenario has been called
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
        if (requestCode == 3){
            val msg =data?.getStringExtra(VIEWING)
            if (msg != null) {
                botResponse(msg)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
