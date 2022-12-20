package com.example.messengernew

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.messengernew.activities.RegisterActivity
import com.example.messengernew.databinding.ActivityMainBinding
import com.example.messengernew.ui.fragments.ChatsFragment
import com.example.messengernew.ui.fragments.EnterPhoneNumberFragment
import com.example.messengernew.ui.objects.AppDriver
import com.example.messengernew.utils.*

open class MainActivity : RegisterActivity() {
    private lateinit var mBinding: ActivityMainBinding
    lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    open fun init() {
        initFields()
        initFunc()
    }

    private fun initFunc() {
        setSupportActionBar(mToolbar)
        APP_DRIVER.create()
        if (AUTH.currentUser != null) {
            changeFragment(ChatsFragment())
        } else {
            changeFragment(EnterPhoneNumberFragment())
        }
    }


    private fun initFields() {
        mToolbar = mBinding.mainToolBar
        APP_DRIVER = AppDriver(this, mToolbar)
        initFirebase()
    }

    override fun onStop() {
        super.onStop()
        AppState.updateState(AppState.OFFLINE, this.baseContext)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (ContextCompat.checkSelfPermission(
                this,
                READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            initContacts(this)
        }
    }
}