package com.example.messengernew.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.example.messengernew.R
import com.example.messengernew.utils.AUTH
import com.example.messengernew.utils.USER
import com.example.messengernew.utils.changeFragment
import com.mikepenz.materialdrawer.AccountHeader
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment(private val header: AccountHeader) :
    BaseFragment(R.layout.fragment_settings) {

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        initFields()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_exit -> {
                AUTH.signOut()
                changeFragment(EnterPhoneNumberFragment())
            }
            R.id.settings_menu_change_name -> changeFragment(ChangeNameFragment(header))
        }
        return true
    }


    private fun initFields() {
        settings_full_name.text = USER.fullName
        settings_phone_number.text = USER.phone
        settings_status.text = USER.status
        settings_username.text = USER.userName
        settings_btn_change_username.setOnClickListener { changeFragment(ChangeUsernameFragment()) }
    }
}