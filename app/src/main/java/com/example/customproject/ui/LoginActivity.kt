package com.example.customproject.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.customproject.R
import com.example.customproject.utils.Constants
import com.example.customproject.utils.Constants.LOGGING

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val submit = findViewById<Button>(R.id.btn_submit)
        val cancel  = findViewById<Button>(R.id.btn_cancel)
        val username = findViewById<EditText>(R.id.username)
        val psw = findViewById<EditText>(R.id.psw)
        cancel.setOnClickListener {
            val returnIntent = Intent().apply {
                putExtra(Constants.VERIFY, "verify incomplete")
            }
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }

        submit.setOnClickListener {
            if (username.text.toString() != "" && psw.text.toString() != "") {
                if (username.text.toString() == "secret" && psw.text.toString() =="hahaha") {
                    val result = intent.getStringExtra(LOGGING)
                    if (result == "viewappointment"){
                        val intent = Intent(this@LoginActivity, AdminAppointment::class.java)
                        startActivityForResult(intent, 1)
                    } else {
                        val intent = Intent(this@LoginActivity, AdminTimeTable::class.java)
                        startActivityForResult(intent, 1)
                    }
                } else {
                    Toast.makeText(this, "Incorrect information. Please try again!", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Please make sure all the information are entered ", Toast.LENGTH_LONG).show()
            }
        }
    }
    override fun onBackPressed() {
        val returnIntent = Intent().apply {
            putExtra(Constants.VERIFY, "verify incomplete")
        }
        setResult(Activity.RESULT_OK, returnIntent)
        super.onBackPressed()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // get the values from the second activity
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        // request code to determine which scenario has been called
        if (requestCode == 1){
            val msg =data?.getStringExtra(Constants.VIEWING)
            if (msg != null) {
                val returnIntent = Intent().apply {
                    putExtra(Constants.VERIFY, msg)
                }
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        }
    }
}