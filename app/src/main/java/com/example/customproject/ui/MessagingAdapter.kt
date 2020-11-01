package com.example.customproject.ui
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.customproject.R
import com.example.customproject.data.Message
import com.example.customproject.utils.Constants.RECEIVE_ID
import com.example.customproject.utils.Constants.SEND_ID
import kotlinx.android.synthetic.main.message_item.view.*

class MessagingAdapter: RecyclerView.Adapter<MessagingAdapter.MessageViewHolder>() {
    var messageList = mutableListOf<Message>()
    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currMsg = messageList[position]
        //check whether the current message is send or receive message
        when (currMsg.id){
            //if the message is a send message, then the bot message disappears
            SEND_ID->{
                holder.itemView.tv_message.apply{
                    text = currMsg.message
                    visibility = View.VISIBLE
                }
                holder.itemView.tv_bot_message.visibility = View.GONE
            }
            //if the message is a receive message, then the tv message disappears
            RECEIVE_ID ->{
                holder.itemView.tv_bot_message.apply{
                    text = currMsg.message
                    visibility = View.VISIBLE
                }
                holder.itemView.tv_message.visibility = View.GONE
            }
        }
    }

    fun addMessage(message: Message){
        // add the new message
        this.messageList.add(message)
        // bring to the newest message.
        notifyItemInserted(messageList.size)
    }
    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener {
                // remove the item from the the position it was clicked on which get the most recent position of the item you're clicking on
                messageList.removeAt(adapterPosition)
                // give a nice animation
                notifyItemRemoved(adapterPosition)
            }
        }
    }
}