package com.example.messengernew.utils

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.messengernew.R
import com.example.messengernew.ui.fragments.BaseFragment

fun Fragment.showToast(message: String) {
    Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.changeFragment(newFragment: Fragment) {
    parentFragmentManager.beginTransaction()
        .addToBackStack(null)
        .replace(R.id.dataContainer, newFragment)
        .commit()
}

fun Fragment.changeFragment(newFragment: BaseFragment) {
    parentFragmentManager.beginTransaction()
        .addToBackStack(null)
        .replace(R.id.dataContainer, newFragment)
        .commit()
}

fun AppCompatActivity.changeFragment(newFragment: Fragment) {
    supportFragmentManager.beginTransaction()
        .addToBackStack(null)
        .replace(R.id.dataContainer, newFragment)
        .commit()
}

fun AppCompatActivity.changeFragment(newFragment: BaseFragment) {
    supportFragmentManager.beginTransaction()
        .addToBackStack(null)
        .replace(R.id.dataContainer, newFragment)
        .commit()
}