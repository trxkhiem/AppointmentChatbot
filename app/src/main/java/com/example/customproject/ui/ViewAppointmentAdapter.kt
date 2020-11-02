package com.example.customproject.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.customproject.R
import com.example.customproject.data.Appointment
import com.example.customproject.data.Timetable
import com.google.android.material.snackbar.Snackbar

class ViewAppointmentAdapter(): RecyclerView.Adapter<ViewAppointmentAdapter.ViewHolder>() {

    private var appointments = emptyList<Appointment>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.appointment_item , parent, false) as View
        return ViewHolder(view)
    }
    /**Get the size of the list**/
    override fun getItemCount(): Int = appointments.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = appointments.get(position)
        holder.bind(item)
    }

    fun setData(appointment : List<Appointment>){
        this.appointments = appointment
        notifyDataSetChanged()
    }
    inner class ViewHolder(val v: View): RecyclerView.ViewHolder(v), View.OnClickListener{
        private lateinit var item: Appointment         //Declare properties in a class
        // setting onclick listener associates with the view
        init {
            itemView.setOnClickListener(this)
        }

        val name = v.findViewById<TextView>(R.id.name)
        val email = v.findViewById<TextView>(R.id.email)
        val phone = v.findViewById<TextView>(R.id.phone)
        val reason = v.findViewById<TextView>(R.id.Reasoning)
        val day = v.findViewById<TextView>(R.id.day)
        val time = v.findViewById<TextView>(R.id.usertime)
        /**To display each item of the list on the recycle view**/
        fun bind(item: Appointment) {
            this.item = item
            name.text = this.item.fName
            email.text = this.item.email
            phone.text = this.item.phone
            reason.text = this.item.reason
            day.text = this.item.day
            time.text = this.item.time
        }
        override fun onClick(v: View) {
        }
    }
}