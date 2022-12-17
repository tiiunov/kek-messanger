package com.example.messengernew.ui.fragments

import androidx.fragment.app.Fragment
import com.example.messengernew.MainActivity
import com.example.messengernew.R
import com.example.messengernew.models.User
import com.example.messengernew.utils.*

class ChatsFragment :
    Fragment(R.layout.fragment_chats) {

    override fun onResume() {
        super.onResume()
        activity?.title = "Чаты"
        initUser()
        initContacts(activity as MainActivity)
        AppState.updateState(AppState.ONLINE, (activity as MainActivity).baseContext)
    }

    private fun initUser() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(UID)
            .addListenerForSingleValueEvent(ValueEventListenerImpl {
                USER = it.getValue(User::class.java) ?: User()
                APP_DRIVER.updateHeader()
            })
    }
}