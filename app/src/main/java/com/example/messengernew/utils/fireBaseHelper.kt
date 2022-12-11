package com.example.messengernew.utils

import com.example.messengernew.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

lateinit var AUTH: FirebaseAuth
lateinit var UID: String
lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var USER: User

const val NODE_USERS = "users"
const val CHILD_ID = "id"
const val CHILD_USER_NAME = "userName"
const val CONSTANT_CHILD_PHONE = "phone"
const val CHILD_FULL_NAME= "fullName"
const val NODE_USERNAMES = "usernames"
const val CHILD_STATE = "state"

fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
    UID = AUTH.currentUser?.uid.toString()
    USER = User()
}