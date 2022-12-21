package com.example.messengernew.ui.fragments

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messengernew.MainActivity
import com.example.messengernew.R
import com.example.messengernew.adapters.SingleChatAdapter
import com.example.messengernew.models.User
import com.example.messengernew.utils.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_single_chat.*
import kotlinx.android.synthetic.main.toolbar_info.view.*


class SingleChatFragment(val contact: ContactsFragment.Contact) :
    BaseFragment(R.layout.fragment_single_chat) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: RecyclerView.Adapter<SingleChatAdapter.ChatHolder>
    private lateinit var mListenerToolBar: ValueEventListenerImpl
    private lateinit var mReceivingUser: User
    private lateinit var mToolBarInfo: View
    private lateinit var mRefUser: DatabaseReference

    override fun onResume() {
        super.onResume()
        mToolBarInfo = (activity as MainActivity).mToolbar.toolbar_info
        mToolBarInfo.visibility = View.VISIBLE
        mListenerToolBar = ValueEventListenerImpl {
            mReceivingUser = it.getValue(User::class.java) ?: User()
            initToolBar()
        }
        mRefUser = REF_DATABASE_ROOT.child(NODE_USERS).child(contact.id)
        mRefUser.addValueEventListener(mListenerToolBar)
        chat_btn_send.setOnClickListener {
            val message = chat_input_message.text.toString()
            if (message.isNotEmpty()) {
                sendMessage(message, contact.id, (activity as MainActivity))
                chat_input_message.setText("")
                updateRecycleView()
            }
        }
        updateRecycleView()
    }

    private fun initToolBar() {
        mToolBarInfo.contact_chat_fullName.text = mReceivingUser.fullName
        mToolBarInfo.contact_chat_status.text = mReceivingUser.state
    }

    private fun updateRecycleView() {
        recyclerView = chat_recycle_view
        val contactMessages = arrayListOf<Message>()
        REF_DATABASE_ROOT.child(NODE_MESSAGES).child(USER.id).child(contact.id)
            .addListenerForSingleValueEvent(ValueEventListenerImpl {
                it.children.forEach { message ->
                    contactMessages.add(createMessage(message))
                    createAdapter(contactMessages)
                }
            })
    }

    private fun createMessage(message: DataSnapshot): Message {
        val text = message.child(CHILD_TEXT).value.toString()
        val author = message.child(CHILD_AUTHOR).value.toString()

        return Message(text, author)
    }

    private fun createAdapter(contactMessages: ArrayList<Message>) {
        mAdapter = SingleChatAdapter(contactMessages)
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.scrollToPosition(mAdapter.itemCount - 1);
    }

    override fun onPause() {
        super.onPause()
        mToolBarInfo.visibility = View.GONE
        mRefUser.removeEventListener(mListenerToolBar)
    }

    class Message(val text: String, val author: String)
}