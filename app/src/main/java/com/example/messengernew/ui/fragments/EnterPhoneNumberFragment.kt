package com.example.messengernew.ui.fragments

import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_enter_phone_number.*
import com.example.messengernew.R
import com.example.messengernew.utils.changeFragment
import com.example.messengernew.utils.showToast


class EnterPhoneNumberFragment : BaseFragment(R.layout.fragment_enter_phone_number) {
    override fun onStart() {
        super.onStart()
        register_btn_next.setOnClickListener { sendCode() }
    }

    private fun sendCode() {
        if (register_input_phone_number.text.toString().isEmpty()) {
            showToast(getString(R.string.register_toast_enter_phone))
        } else {
            changeFragment(EnterCodeFragment())
        }
    }
}