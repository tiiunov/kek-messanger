package com.example.messengernew.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messengernew.R
import com.example.messengernew.ui.fragments.ContactsFragment

class ContactsAdapter(private val contacts: ArrayList<ContactsFragment.Contact>) :
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fullName: TextView = itemView.findViewById(R.id.contact_fullName)
        val status: TextView = itemView.findViewById(R.id.contact_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.contact_item, parent, false)

        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(viewHolder: ContactsAdapter.ViewHolder, position: Int) {
        val contact: ContactsFragment.Contact = contacts[position]
        val textView = viewHolder.fullName
        textView.text = contact.fullName
        val textViewStatus = viewHolder.status
        textViewStatus.text = contact.status
    }

    override fun getItemCount(): Int {
        return contacts.size
    }
}