package com.e.booker.viewmodel.activityviewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.booker.view.ui.activities.AdminPanelActivity
import com.e.booker.view.ui.activities.HomeActivity
import com.e.booker.view.ui.activities.LoginAcitivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginViewModel(var context: Context): ViewModel() {

    private var activity: LoginAcitivity = LoginAcitivity()
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    var liveData = MutableLiveData<State>()


    fun loginUser(email: String, password: String){
        liveData.value = State.ShowLoading
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(object : OnCompleteListener<AuthResult>{
            override fun onComplete(p0: Task<AuthResult>) {
                if(p0.isSuccessful){
                    liveData.value = State.HideLoading
                    val currentUser: String = mAuth.currentUser!!.uid
                    val admin: String = "sEy6rbovv0PrzPtoiK7Gq0fdTbI3"

                    if(currentUser == admin){
                        val adminUser: FirebaseUser = mAuth.currentUser!!
                        adminUpdateUI(adminUser)
                    }else{
                        Log.e("HAPPY","Signed Successfully!")
                        val user: FirebaseUser = mAuth.currentUser!!
                        updateUI(user)
                    }
                }else{
                    liveData.value = State.HideLoading
                    Toast.makeText(context, p0.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun updateUI(user: FirebaseUser){
        if(user != null){
            if(user.uid == "sEy6rbovv0PrzPtoiK7Gq0fdTbI3"){
                adminUpdateUI(user)
                activity.finish()
            }else{
                Log.e("TAG","Signed Successfully!")
                Toast.makeText(context, "Signed Successfully!", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, HomeActivity::class.java)
                context.startActivity(intent)
                activity.finish()
            }
        }else{
            Toast.makeText(activity,"You don't signed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun adminUpdateUI(user: FirebaseUser){
        if(user != null){
            Log.e("TAG","ADMIN PANEL ACTIVATED!")
            Toast.makeText(context, "ADMIN PANEL ACTIVATED!", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, AdminPanelActivity::class.java)
            context.startActivity(intent)
            activity.finish()
        }else{
            Toast.makeText(context,"You don't signed", Toast.LENGTH_SHORT).show()
        }
    }

    sealed class State(){
        object ShowLoading: State()
        object HideLoading: State()
    }



}