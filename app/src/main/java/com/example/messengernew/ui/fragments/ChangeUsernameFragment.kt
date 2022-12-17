package com.example.messengernew.ui.fragments

import com.example.messengernew.R
import com.example.messengernew.utils.*
import kotlinx.android.synthetic.main.fragment_change_username.*

class ChangeUsernameFragment : BaseChangeFragment(R.layout.fragment_change_username) {

    lateinit var mNewUsername: String

    override fun onResume() {
        super.onResume()
        settings_input_username.setText(USER.userName)
    }


    override fun change() {
        mNewUsername = settings_input_username.text.toString().lowercase()
        if (mNewUsername.isEmpty()) {
            showToast("username не должен быть пустым")
        } else {
            REF_DATABASE_ROOT.child(NODE_USERNAMES)
                .addListenerForSingleValueEvent(ValueEventListenerImpl {
                    if (it.hasChild(mNewUsername)) {
                        showToast("пользователь с таким username уже существует")
                    } else {
                        changeUsername()
                    }
                })
        }
    }

    private fun changeUsername() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(mNewUsername).setValue(UID)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    updateCurrentUsername()
                }
            }
    }

    private fun updateCurrentUsername() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_USER_NAME).setValue(mNewUsername)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    deleteOldUsername()
                } else {
                    showToast(it.exception?.message.toString())
                }
            }
    }

    private fun deleteOldUsername() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(USER.userName).removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast("username изменен")
                    parentFragmentManager.popBackStack()
                    USER.userName = mNewUsername
                } else {
                    showToast(it.exception?.message.toString())
                }
            }
    }
}