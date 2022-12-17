package com.example.messengernew.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.messengernew.R
import com.example.messengernew.databinding.ActivityRegisterBinding
import com.example.messengernew.ui.fragments.EnterPhoneNumberFragment
import com.example.messengernew.utils.changeFragment
import com.example.messengernew.utils.initFirebase

open class RegisterActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityRegisterBinding
    private lateinit var mToolBar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initFirebase()
    }

    override fun onStart() {
        super.onStart()
        mToolBar = mBinding.registerToolBar
        setSupportActionBar(mToolBar)
        title = getString(R.string.messenger_name)
        changeFragment(EnterPhoneNumberFragment())
    }
}