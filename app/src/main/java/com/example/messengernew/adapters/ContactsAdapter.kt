package com.example.messengernew.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messengernew.R
import com.example.messengernew.ui.fragments.BaseFragment
import com.example.messengernew.ui.fragments.ContactsFragment
import com.example.messengernew.ui.fragments.SingleChatFragment
import com.example.messengernew.utils.changeFragment

class ContactsAdapter(
    private val contacts: ArrayList<ContactsFragment.Contact>,
    private val parentFragment: BaseFragment
) :
    RecyclerView.Adapter<ContactsAdapter.ContactHolder>() {

    inner class ContactHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fullName: TextView = itemView.findViewById(R.id.contact_fullName)
        val status: TextView = itemView.findViewById(R.id.contact_status)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.contact_item, parent, false)

        return ContactHolder(contactView)
    }

    override fun onBindViewHolder(viewHolder: ContactHolder, position: Int) {
        val contact: ContactsFragment.Contact = contacts[position]
        val textView = viewHolder.fullName
        textView.text = contact.fullName
        val textViewStatus = viewHolder.status
        textViewStatus.text = contact.status
        viewHolder.itemView.setOnClickListener {
            parentFragment.changeFragment(SingleChatFragment(contact))
        }
    }

    override fun getItemCount(): Int {
        return contacts.size
    }
}