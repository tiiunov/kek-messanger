package com.example.messengernew.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messengernew.R
import com.example.messengernew.ui.fragments.SingleChatFragment
import com.example.messengernew.utils.USER

class SingleChatAdapter(private val contactMessages: ArrayList<SingleChatFragment.Message>) :
    RecyclerView.Adapter<SingleChatAdapter.ChatHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.message_item, parent, false)

        return ChatHolder(contactView)
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        val message: SingleChatFragment.Message = contactMessages[position]
        val textViewUser = holder.userMessage
        val textViewReceiver = holder.receiverMessage

        if (message.author == USER.id) {
            textViewUser.text = message.text
            textViewUser.visibility = View.VISIBLE
            textViewReceiver.visibility = View.GONE
        } else {
            textViewReceiver.text = message.text
            textViewReceiver.visibility = View.VISIBLE
            textViewUser.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return contactMessages.size
    }

    inner class ChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userMessage: TextView = itemView.findViewById(R.id.chat_user_message)
        val receiverMessage: TextView = itemView.findViewById(R.id.chat_received_message)
    }
}