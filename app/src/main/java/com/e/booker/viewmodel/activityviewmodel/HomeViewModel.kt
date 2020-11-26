package com.e.booker.viewmodel.activityviewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideContext
import com.e.booker.view.ui.activities.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import de.hdodenhof.circleimageview.CircleImageView

class HomeViewModel(val context: Context): ViewModel() {

    private var activity = HomeActivity()

    fun showImage(image: CircleImageView){
        val currentUser = FirebaseAuth.getInstance().currentUser!!.uid

        val reference = FirebaseDatabase
                .getInstance()
                .reference
                .child("Users")
                .child(currentUser)

        reference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val mImage: String = snapshot.child("image").value.toString()

                Glide.with(context.applicationContext)
                        .load(mImage)
                        .into(image)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("LOAD_FAILED", error.message)
            }
        })
    }
}