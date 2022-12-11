package com.example.messengernew.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.messengernew.MainActivity
import com.example.messengernew.R
import com.example.messengernew.ui.objects.AppDriver
import com.example.messengernew.utils.*
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import kotlinx.android.synthetic.main.fragment_change_name.*

class ChangeNameFragment(val header: AccountHeader) : BaseChangeFragment(R.layout.fragment_change_name) {

    override fun onResume() {
        super.onResume()
        val fullNameList = USER.fullName.split(" ")
        settings_input_name.setText(fullNameList[0])
        if (fullNameList.size > 1) {
            settings_input_surname.setText(fullNameList[1])
        } else {
            settings_input_surname.setText("")
        }
    }

    override fun change() {
        val name = settings_input_name.text.toString()
        val surname = settings_input_surname.text.toString()

        if (name.isEmpty()) {
            showToast(getString(R.string.setting_toast_name_is_empty))
        } else {
            val fullName = "$name $surname"
            REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_FULL_NAME).setValue(fullName)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast(getString(R.string.toast_data_update))
                        USER.fullName = fullName
                        updateHeader()
                        parentFragmentManager.popBackStack()
                    }
            }
        }
    }

    private fun updateHeader() {
        val mCurrentProfile = ProfileDrawerItem()
            .withName(USER.fullName)
            .withEmail(USER.phone)
            .withIdentifier(200)

        header.updateProfile(mCurrentProfile)
    }
}