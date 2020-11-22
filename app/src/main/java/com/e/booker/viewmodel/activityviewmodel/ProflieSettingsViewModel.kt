package com.e.booker.viewmodel.activityviewmodel

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
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

    fun showAboutBooker(){
        MaterialDialog(context, BottomSheet(LayoutMode.WRAP_CONTENT)).show {
            Toast.makeText(context, "Bottom Sheet", Toast.LENGTH_SHORT).show()
        }
    }
}