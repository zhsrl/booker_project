package com.e.booker.viewmodel.fragmentviewmodel

import android.content.Context
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import de.hdodenhof.circleimageview.CircleImageView

class ProfileChangeViewModel(val context: Context): ViewModel() {

    val mAuth = FirebaseAuth.getInstance()

    fun showCurrentData(name: EditText, surname: EditText, profileImage: CircleImageView){
        val currentUser = mAuth.currentUser

        if(currentUser != null){
            val reference = FirebaseDatabase
                    .getInstance()
                    .getReference()
                    .child("Users")
                    .child(currentUser.uid)

            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val mName: String = snapshot.child("name").value.toString()
                    val mSurname: String = snapshot.child("surname").value.toString()
                    val mImage: String = snapshot.child("image").value.toString()

                    name.setText(mName, TextView.BufferType.EDITABLE)
                    surname.setText(mSurname, TextView.BufferType.EDITABLE)

                    Glide.with(context)
                            .load(mImage)
                            .into(profileImage)

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