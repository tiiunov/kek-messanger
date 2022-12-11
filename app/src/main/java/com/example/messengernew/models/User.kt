package com.example.messengernew.models

data class User(
    val id: String = "",
    var userName: String = "",
    var bio: String = "",
    var fullName: String = "",
    var phone: String = "",
    var status: String = "",
    var photoUrl: String = ""
)