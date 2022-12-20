package com.example.messengernew.ui.fragments

import android.view.View
import com.example.messengernew.MainActivity
import com.example.messengernew.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class SingleChatFragment(contact: ContactsFragment.Contact) : BaseFragment(R.layout.fragment_single_chat) {
    override fun onResume() {
        super.onResume()
        (activity as MainActivity).mToolbar.toolbar_info.visibility = View.VISIBLE
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).mToolbar.toolbar_info.visibility = View.GONE
    }
}