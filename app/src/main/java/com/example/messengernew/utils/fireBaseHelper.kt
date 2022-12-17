package com.example.messengernew.utils

import android.app.Activity
import android.provider.ContactsContract
import com.example.messengernew.models.CommonModel
import com.example.messengernew.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

lateinit var AUTH: FirebaseAuth
lateinit var UID: String
lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var USER: User

const val NODE_USERS = "users"
const val CHILD_ID = "id"
const val CHILD_USER_NAME = "userName"
const val CONSTANT_CHILD_PHONE = "phone"
const val CHILD_FULL_NAME = "fullName"
const val NODE_USERNAMES = "usernames"
const val CHILD_STATE = "state"
const val NODE_PHONES = "phones"
const val NODE_PHONE_CONTACTS = "phoneContacts"

fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
    UID = AUTH.currentUser?.uid.toString()
    USER = User()
}

fun initContacts(activity: Activity) {
    CoroutineScope(Dispatchers.IO).launch {
        if (checkPermission(READ_CONTACTS, activity)) {
            val contacts = arrayListOf<CommonModel>()
            val cursor = activity.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                null
            )
            cursor?.let {
                while (it.moveToNext()) {
                    val idxName = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                    val idxPhone = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    val fullName = it.getString(if (idxName < 0) 0 else idxName)
                    val phone = it.getString(if (idxPhone < 0) 0 else idxPhone)

                    val newModel = CommonModel()
                    newModel.fullName = fullName
                    newModel.phone = phone.replace(Regex("[\\s, -]"), "")
                    contacts.add(newModel)
                }
            }
            cursor?.close()

            updatePhonesToDb(contacts)
        }
    }
}

fun updatePhonesToDb(contacts: ArrayList<CommonModel>) {
    REF_DATABASE_ROOT.child(NODE_PHONES).addListenerForSingleValueEvent(ValueEventListenerImpl {
        it.children.forEach { dbContact ->
            contacts.forEach { contact ->
                if (dbContact.key == contact.phone) {
                    REF_DATABASE_ROOT.child(NODE_PHONE_CONTACTS).child(USER.id)
                        .child(dbContact.value.toString()).child(CHILD_ID)
                        .setValue(dbContact.value.toString())
                }
            }
        }
    })
}
