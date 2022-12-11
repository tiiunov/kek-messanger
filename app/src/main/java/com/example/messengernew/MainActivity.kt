package com.example.messengernew

import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.messengernew.activities.RegisterActivity
import com.example.messengernew.databinding.ActivityMainBinding
import com.example.messengernew.models.User
import com.example.messengernew.ui.fragments.ChatsFragment
import com.example.messengernew.ui.fragments.EnterPhoneNumberFragment
import com.example.messengernew.ui.objects.AppDriver
import com.example.messengernew.utils.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_settings.*

open class MainActivity : RegisterActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mToolbar: Toolbar
    lateinit var mAppDriver: AppDriver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onStart() {
        super.onStart()
        initFields()
        initFunc()
        AppState.updateState(AppState.ONLINE, this.baseContext)
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


    private fun initFields() {
        mToolbar = mBinding.mainToolBar
        mAppDriver = AppDriver(this, mToolbar)
        initFirebase()
        initUser()
        initContacts()
    }

    private fun initContacts() {
        if (checkPermission(READ_CONTACTS, this)) {
            showToast("чтение контактов...")
        }
    }

    private fun initUser() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(UID)
            .addListenerForSingleValueEvent(ValueEventListenerImpl{
                USER = it.getValue(User::class.java) ?: User()
                mAppDriver.updateHeader()
            })
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
            initContacts()
        }
    }
}