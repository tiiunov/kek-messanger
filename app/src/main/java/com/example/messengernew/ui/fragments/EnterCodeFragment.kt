package com.example.messengernew.ui.fragments

import com.example.messengernew.MainActivity
import com.example.messengernew.R
import com.example.messengernew.activities.RegisterActivity
import com.example.messengernew.utils.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_enter_code.*

class EnterCodeFragment(val mPhoneNumber: String, val id: String) : BaseFragment(R.layout.fragment_enter_code) {

    override fun onStart() {
        super.onStart()
        (activity as RegisterActivity).title = mPhoneNumber
        register_input_code.addTextChangedListener(TextWatcherImpl {
            val code = register_input_code.text.toString()
            if (code.length == 6) {
                verify_code(code)
            }
        })
    }

    private fun verify_code(code: String) {
        val credential = PhoneAuthProvider.getCredential(id, code)
        AUTH.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                val uid = AUTH.currentUser?.uid.toString()
                val dataMap = mutableMapOf<String, Any>()
                dataMap[CHILD_ID] = uid
                dataMap[CONSTANT_CHILD_PHONE] = mPhoneNumber
                dataMap[CHILD_USER_NAME] = uid

                REF_DATABASE_ROOT.child(NODE_PHONES).child(mPhoneNumber).setValue(uid)
                    .addOnFailureListener { showToast(it.message.toString()) }
                    .addOnCompleteListener {
                        REF_DATABASE_ROOT.child(NODE_USERS).child(uid).updateChildren(dataMap)
                            .addOnSuccessListener {
                                showToast("Добро пожаловать!")
                                (activity as RegisterActivity).changeFragment(ChatsFragment())
                            }
                            .addOnFailureListener{showToast(it.message.toString())}
                    }
                } else {
                showToast(it.exception?.message.toString())
            }
        }
    }
}