package com.example.messengernew.utils

import android.content.Context
import android.widget.Toast

enum class AppState(val state: String) {
    ONLINE("онлайн"),
    OFFLINE("не в сети"),
    TYPING("печатает");

    companion object {
        fun updateState(newState: AppState, context: Context) {
            if (AUTH.currentUser != null) {
                REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_STATE)
                    .setValue(newState.state)
                    .addOnFailureListener {
                        Toast.makeText(context, it.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                    .addOnCompleteListener { USER.state = newState.state }
            }
        }
    }
}