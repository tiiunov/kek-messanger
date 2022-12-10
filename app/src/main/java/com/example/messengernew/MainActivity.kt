package com.example.messengernew

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.messengernew.activities.RegisterActivity
import com.example.messengernew.databinding.ActivityMainBinding
import com.example.messengernew.ui.fragments.ChatsFragment
import com.example.messengernew.ui.fragments.EnterPhoneNumberFragment
import com.example.messengernew.ui.objects.AppDriver
import com.example.messengernew.utils.AUTH
import com.example.messengernew.utils.changeFragment
import com.example.messengernew.utils.initFirebase
import com.google.firebase.auth.FirebaseAuth

open class MainActivity : RegisterActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mToolbar: Toolbar
    private lateinit var mAppDriver: AppDriver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onStart() {
        super.onStart()
        inintFields()
        initFunc()
    }

    private fun initFunc() {
        setSupportActionBar(mToolbar)
        mAppDriver.create()
        if (AUTH.currentUser != null) {
            changeFragment(ChatsFragment())
        } else {
            changeFragment(EnterPhoneNumberFragment())
        }
    }


    private fun inintFields() {
        mToolbar = mBinding.mainToolBar
        mAppDriver = AppDriver(this, mToolbar)
        initFirebase()
    }
}