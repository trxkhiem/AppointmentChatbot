package com.example.customproject.utils


import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

object Time {
    fun timing():String{
        val timing = Timestamp(System.currentTimeMillis())
        val dateFormat = SimpleDateFormat("HH:mm")
        val time = dateFormat.format(Date(timing.time))

        return time.toString()
    }
}