package com.example.customproject.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*
@Parcelize
@Entity (tableName = "Appointment")
data class Appointment( @PrimaryKey (autoGenerate = true)
                        var appointmentID: Int, var fName: String ="", var email: String ="", var phone: String ="",
                       var reason: String = "", var day : String = "", var time: String = "") : Parcelable {
}