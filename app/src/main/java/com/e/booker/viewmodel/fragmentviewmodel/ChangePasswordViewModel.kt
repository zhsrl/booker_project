package com.e.booker.viewmodel.fragmentviewmodel

import android.content.Context
import android.content.Intent
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.e.booker.view.ui.activities.auth.LoginAcitivity
import com.e.booker.view.ui.activities.home.ProfileSettingsActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ChangePasswordViewModel(val context: Context): ViewModel() {

    private var activity = ProfileSettingsActivity()
    val mAuth = FirebaseAuth.getInstance()



    fun changeData(currentPassword: EditText, newPassword: EditText){



        val currentUser = mAuth.currentUser

        if(currentPassword.text.isNotEmpty()){
            if(currentUser != null && currentUser.email != null){
                val credential = EmailAuthProvider.getCredential(currentUser.email!!, currentPassword.text.toString())

                currentUser.reauthenticate(credential).addOnCompleteListener { p0 ->
                    if (p0.isSuccessful) {
                        Toast.makeText(context, "Re-auth success!", Toast.LENGTH_SHORT).show()

                        currentUser.updatePassword(newPassword.text.toString())
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {

                                        val newPass = newPassword.text.toString()

                                        Toast.makeText(
                                                context,
                                                "Құпия сөз өзгертілді!, Қайта кіріңіз.",
                                                Toast.LENGTH_SHORT
                                        ).show()

                                        val passwordReference = FirebaseDatabase
                                                .getInstance()
                                                .reference
                                                .child("Users")
                                                .child(currentUser.uid)
                                                .child("password")

                                        passwordReference.setValue(newPass)

                                        mAuth.signOut()
                                        val intent = Intent(context, LoginAcitivity::class.java)
                                        context.startActivity(intent)
                                        activity.finish()
                                    }
                                }
                    }
                }
            }
        }
    }
}