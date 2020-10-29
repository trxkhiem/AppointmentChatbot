package com.example.customproject.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.customproject.R
import com.example.customproject.utils.Constants.UPDATE
import com.example.customproject.utils.Constants.VERIFY

class verifyActivity : AppCompatActivity() {
    private lateinit var appointmentVM: appointmentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)
        val submit = findViewById<Button>(R.id.btn_submit)
        val cancel  = findViewById<Button>(R.id.btn_cancel)
        val email = findViewById<EditText>(R.id.email)
        val phone = findViewById<EditText>(R.id.phoneNum)
        appointmentVM = ViewModelProvider(this).get(appointmentViewModel::class.java)
        submit.setOnClickListener {
            val checkEmail = email.text.toString()
            val checkPhone = phone.text.toString()
            if (!checkFields(checkEmail, checkPhone)){
                Toast.makeText(this, "Please make sure all the information are entered ", Toast.LENGTH_LONG).show()
            } else {
                appointmentVM.getAppointment(checkEmail, checkPhone).observe(this, Observer {
                    var appointment = it
                    if (appointment == null){
                        Toast.makeText(this, "There is no appointment that matched you provided information. Please try again!! ", Toast.LENGTH_LONG).show()
                    } else {
                        val intent = Intent(this@verifyActivity, DetailsActivity::class.java)
                        intent.putExtra(VERIFY, appointment)
                        startActivityForResult(intent, 1)
                    }
                })
            }
        }

        cancel.setOnClickListener {
            val returnIntent = Intent().apply {
                putExtra(VERIFY, "verify incomplete")
            }
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }

    private fun checkFields (name: String, email: String) : Boolean{
        return (name != "" && email != "")
    }
    override fun onBackPressed() {
        val returnIntent = Intent().apply {
            putExtra(VERIFY, "verify incomplete")
        }
        setResult(Activity.RESULT_OK, returnIntent)
        super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        if (requestCode == 1){
            val msg =data?.getStringExtra(UPDATE)
            if (msg != null) {
                val returnIntent = Intent().apply {
                    putExtra(VERIFY, msg)
                }
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        }
    }
}