package com.example.customproject.utils

import com.example.customproject.utils.Constants.ADD_APPOINTMENT
import com.example.customproject.utils.Constants.CANCEL_APPOINTMENT
import com.example.customproject.utils.Constants.OPEN_GOOGLE
import com.example.customproject.utils.Constants.OPEN_SEARCH
import com.example.customproject.utils.Constants.UPDATE_APPOINTMENT
import com.example.customproject.utils.Constants.VIEW_APPOINTMENT
import com.example.customproject.utils.Constants.VIEW_TIMETABLE
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.sql.Date

object BotResponse {
    fun basicResponses (_msg: String): String{
        val msg =_msg.toLowerCase()
        val rand = (0..2).random()
        return when{
            //Hello
            msg.contains("hello") || msg.contains("hi") || msg.contains("hey") -> {
                when (rand) {
                    0 -> "Hey nice to meet you, How can I help you today?"
                    1 -> "Hello there, how can I help you today?"
                    2 -> "Hi there, how can I help you today?"
                    else -> "Error"
                }
            }

            msg.contains("view complete") -> {
                "Is there anything else that I can help you with?"
            }

            msg.contains("update complete") -> {
                "You have successfully updated the appointment. Is there anything else that I can help you with?"
            }

            msg.contains("delete complete") -> {
                "You have successfully deleted the appointment. Is there anything else that I can help you with?"
            }

            msg.contains("update incomplete") -> {
                "The process has been cancelled. Is there anything else that I can help you with?"
            }

            msg.contains("add complete") -> {
                "You have successfully added the appointment. Is there anything else that I can help you with?"
            }

            msg.contains("verify incomplete") -> {
                "The verify process is unsuccessful. Is there anything else that I can help you with?"
            }

            msg.contains("add incomplete") -> {
                "The adding process has been cancelled. Is there anythings else that I can help you with?"
            }
            //What time is it?
            msg.contains("time") && msg.contains("?")-> {
                val timer = Timestamp(System.currentTimeMillis())
                val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val date = dateFormat.format(Date(timer.time))
                date.toString()
            }
            //add appointment
            msg.contains("add") || msg.contains("create") || msg.contains("book") && msg.contains("appointment")-> {
                ADD_APPOINTMENT
            }

            // cancel appointment
            msg.contains("cancel") || msg.contains("remove") || msg.contains("delete") && msg.contains("appointment")-> {
                CANCEL_APPOINTMENT
            }

            // view appointment
            msg.contains("view") || msg.contains("see") && msg.contains("appointment")-> {
                VIEW_APPOINTMENT
            }

            // view appointment
            msg.contains("check") && msg.contains("available time")-> {
                VIEW_TIMETABLE
            }

            msg.contains("update appointment")-> {
                UPDATE_APPOINTMENT
            }
            else ->{
                when (rand) {
                    0 -> "I don't understand. Can you please try again..."
                    1 -> "Try asking me something different"
                    2 -> "Sorry I don't know what you means. Please ask me another way"
                    else -> "Error"
                }
            }
        }

    }
}