package com.e.booker.viewmodel.activityviewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.booker.model.User
import com.e.booker.view.ui.activities.auth.LoginAcitivity
import com.e.booker.view.ui.activities.auth.RegistrationActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistrationViewModel(private val context: Context): ViewModel(){
    val activity = RegistrationActivity()

    var liveData = MutableLiveData<State>()


    var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var mDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    fun signUpUser(name: String, surname: String, email: String, password: String){

        liveData.value = State.ShowLoading
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(object : OnCompleteListener<AuthResult>{
                override fun onComplete(p0: Task<AuthResult>) {
                    if (p0.isSuccessful){
                        val user = User(name = name, surname = surname, email = email, password = password)
                        liveData.value = State.HideLoading

                        mDatabase.getReference("Users")
                            .child(FirebaseAuth.getInstance().currentUser!!.uid)
                            .setValue(user)
                            .addOnCompleteListener(object : OnCompleteListener<Void>{
                                override fun onComplete(p0: Task<Void>) {
                                    if(p0.isSuccessful){

                                        liveData.value = State.HideLoading
                                        Toast.makeText(context, "Sign in successful!", Toast.LENGTH_SHORT).show()

                                        val intent = Intent(context, LoginAcitivity::class.java)
                                        context.startActivity(intent)
                                        activity.finish()
                                    }else{
                                        liveData.value = State.HideLoading
                                        Log.e("AUTH_DB_ERROR",p0.exception.toString())
                                    }
                                }
                            })
                    }else{
                        Log.e("AUTH_ERROR",p0.exception.toString())
                        liveData.value = State.HideLoading
                    }
                }
            })



    }

    sealed class State(){
        object HideLoading: State()
        object ShowLoading: State()
    }



}