package com.e.booker.viewmodel.activityviewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import de.hdodenhof.circleimageview.CircleImageView

class ProflieSettingsViewModel(var context: Context) : ViewModel(){

    var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var reference: DatabaseReference

    fun showData(name: TextView, surname: TextView, email: TextView, profileImage: CircleImageView){
        val currentUser = mAuth.currentUser

        if(currentUser != null){
            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.uid)

            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val mName = snapshot.child("name").value.toString()
                    val mSurname = snapshot.child("surname").value.toString()
                    val mEmail = snapshot.child("email").value.toString()
                    val mImage = snapshot.child("image").value.toString()

                    Glide.with(context)
                            .load(mImage)
                            .into(profileImage)

                    name.text = mName
                    surname.text = mSurname
                    email.text = mEmail

                }

                @SuppressLint("LongLogTag")
                override fun onCancelled(error: DatabaseError) {
                    Log.e("View Data(ProfSetViewModel)", error.toString())
                }
            })
        }
    }


}