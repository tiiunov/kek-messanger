package com.example.messengernew.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.example.messengernew.MainActivity
import android.content.Context
import androidx.core.app.ActivityCompat

const val READ_CONTACTS = Manifest.permission.READ_CONTACTS
const val PERMISSION_REQUEST = 200

fun checkPermission(permission: String, activity: Activity): Boolean {
    if (Build.VERSION.SDK_INT > 22 &&
        ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(activity, arrayOf(permission), PERMISSION_REQUEST)
        return false
    } else {
        return true
    }
}