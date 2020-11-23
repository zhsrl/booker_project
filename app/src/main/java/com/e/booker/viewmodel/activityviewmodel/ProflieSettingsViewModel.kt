package com.e.booker.viewmodel.activityviewmodel

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.e.booker.view.ui.activities.auth.LoginAcitivity
import com.e.booker.view.ui.activities.home.ProfileChangeActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.w3c.dom.Text

class ProflieSettingsViewModel(var context: Context) : ViewModel(){

    var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var reference: DatabaseReference

    fun showData(name: TextView, surname: TextView, email: TextView){
        val currentUser = mAuth.currentUser

        if(currentUser != null){
            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.uid)

            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val mName = snapshot.child("name").value.toString()
                    val mSurname = snapshot.child("surname").value.toString()
                    val mEmail = snapshot.child("email").value.toString()

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