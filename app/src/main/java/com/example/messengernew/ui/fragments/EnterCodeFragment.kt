package com.example.messengernew.ui.fragments

import android.widget.Toast
import com.example.messengernew.R
import com.example.messengernew.utils.TextWatcherImpl
import com.example.messengernew.utils.showToast
import kotlinx.android.synthetic.main.fragment_enter_code.*

class EnterCodeFragment : BaseFragment(R.layout.fragment_enter_code) {

    override fun onStart() {
        super.onStart()
        register_input_code.addTextChangedListener(TextWatcherImpl {
            val code = register_input_code.text.toString()
            if (code.length == 6) {
                verify_code(code)
            }
        })
    }

    private fun verify_code(code: String) {
        showToast("verify code")
    }
}