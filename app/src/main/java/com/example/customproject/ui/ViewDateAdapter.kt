package com.example.customproject.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.customproject.R
import com.example.customproject.data.Timetable

class ViewDateAdapter(): RecyclerView.Adapter<ViewDateAdapter.ViewHolder>() {

    private var times = emptyList<Timetable>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.date_item , parent, false) as View
        return ViewHolder(view)
    }
    /**Get the size of the list**/
    override fun getItemCount(): Int = times.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = times.get(position)
        holder.bind(item)
    }

    fun setData(time : List<Timetable>){
        this.times = time
        notifyDataSetChanged()
    }
    inner class ViewHolder(val v: View): RecyclerView.ViewHolder(v), View.OnClickListener{
        private lateinit var item: Timetable          //Declare properties in a class
        // setting onclick listener associates with the view
        init {
            itemView.setOnClickListener(this)
        }
        val image = v.findViewById<ImageView>(R.id.available)
        val title = v.findViewById<TextView>(R.id.Day)
        val time = v.findViewById<TextView>(R.id.time)
        /**To display each item of the list on the recycle view**/
        fun bind(item: Timetable) {
            this.item = item
            val checkNul = this.item.day
            title.text = this.item.day
            time.text = this.item.time
            /**The icon status to determine which image need to display*/
            if(this.item.isAvailable == 0){
                image.setImageResource(R.mipmap.tick_foreground)
            } else {
                image.setImageResource(R.mipmap.cross_foreground)
            }
        }
        /**on click function will be called when user clicks on a specific row
         * A snack bar will show up with the message of corresponding location**/

        override fun onClick(v: View) {
        }

    }
}