package com.example.messengernew.ui.fragments

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messengernew.MainActivity
import com.example.messengernew.R
import com.example.messengernew.adapters.ContactsAdapter
import com.example.messengernew.models.User
import com.example.messengernew.utils.*
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ContactsFragment : BaseFragment(R.layout.fragment_contacts) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: RecyclerView.Adapter<ContactsAdapter.ContactHolder>
    private val userContacts: ArrayList<Contact> = arrayListOf()

    override fun onResume() {
        super.onResume()
        activity?.title = "Контакты"
        initUser()
        initContacts(activity as MainActivity)
        AppState.updateState(AppState.ONLINE, (activity as MainActivity).baseContext)
        initRecycleView()
    }

    private fun initUser() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(UID)
            .addListenerForSingleValueEvent(ValueEventListenerImpl {
                USER = it.getValue(User::class.java) ?: User()
                APP_DRIVER.updateHeader()
            })
    }

    private fun initRecycleView() {
        recyclerView = contacts_recycle_view
        val contactsIds = arrayListOf<String>()
        if (userContacts.size == 0) {
            REF_DATABASE_ROOT.child(NODE_PHONE_CONTACTS).child(USER.id)
                .addListenerForSingleValueEvent(ValueEventListenerImpl {
                    it.children.forEach { id ->
                        contactsIds.add(id.child(CHILD_ID).value.toString())
                    }

                    createContacts(contactsIds)
                })
        } else {
            createAdapter()
        }
    }

    private fun createContacts(contactsIds: ArrayList<String>) {
        CoroutineScope(Dispatchers.IO).launch {
            contactsIds.forEach { contactId ->
                REF_DATABASE_ROOT.child(NODE_USERS).child(contactId)
                    .addListenerForSingleValueEvent(ValueEventListenerImpl {
                        if (it.exists() && it.child(CHILD_FULL_NAME).value != null) {
                            val fullName = it.child(CHILD_FULL_NAME).value.toString()
                            val status = it.child(CHILD_STATE).value.toString()
                            val id = it.child(CHILD_ID).value.toString()
                            userContacts.add(Contact(fullName, status, id))
                            createAdapter()
                        }
                    })
            }
        }
    }

    private fun createAdapter() {
        mAdapter = ContactsAdapter(userContacts, this)
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    class Contact(val fullName: String, val status: String, val id: String)
}