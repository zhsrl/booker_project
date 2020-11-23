package com.e.booker.viewmodel.activityviewmodel

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.ViewModel
import com.e.booker.model.User
import com.e.booker.view.ui.activities.auth.LoginAcitivity
import com.e.booker.view.ui.activities.home.ProfileChangeActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class ProfileChangeViewModel(val context: Context): ViewModel() {

    private var activity = ProfileChangeActivity()
    val mAuth = FirebaseAuth.getInstance()

    fun showCurrentData(name: EditText, surname: EditText){
        val currentUser = mAuth.currentUser

        if(currentUser != null){
            val reference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Users")
                .child(currentUser.uid)

            reference.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val mName: String = snapshot.child("name").value.toString()
                    val mSurname: String = snapshot.child("surname").value.toString()

                    name.setText(mName, TextView.BufferType.EDITABLE)
                    surname.setText(mSurname, TextView.BufferType.EDITABLE)

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("ProfileChange_DB_ERROR", error.message)
                }
            })
        }
    }

    fun changeData(name: EditText, surname: EditText){
        val currentUser = mAuth.currentUser

        val nameReference = FirebaseDatabase
                .getInstance()
                .reference
                .child("Users")
                .child(currentUser!!.uid).child("name")

        val surnameReference = FirebaseDatabase
                .getInstance()
                .reference
                .child("Users")
                .child(currentUser.uid)
                .child("surname")

        val mName: String = name.text.toString()
        val mSurname: String = surname.text.toString()

        nameReference.setValue(mName)
        surnameReference.setValue(mSurname)

        Toast.makeText(context, "Деректер өзгертілді", Toast.LENGTH_SHORT).show()
    }

}