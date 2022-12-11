package com.example.messengernew.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.example.messengernew.MainActivity
import com.example.messengernew.R
import com.example.messengernew.activities.RegisterActivity
import com.example.messengernew.utils.AUTH
import com.example.messengernew.utils.changeFragment

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
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
            R.id.settings_menu_change_name -> changeFragment(ChangeNameFragment())
        }
        return true
    }
}