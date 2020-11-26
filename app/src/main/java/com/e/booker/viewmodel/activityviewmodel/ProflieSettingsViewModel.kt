package com.e.booker.viewmodel.activityviewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import de.hdodenhof.circleimageview.CircleImageView

class ProflieSettingsViewModel(var context: Context) : ViewModel(){

    var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var reference: DatabaseReference

    var liveData = MutableLiveData<State>()

    fun showData(name: TextView, surname: TextView, email: TextView, profileImage: CircleImageView){
        liveData.value = State.ShowLoading
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
                    liveData.postValue(State.Result(name, surname, email, profileImage))
                    liveData.value = State.HideLoading

                }

                @SuppressLint("LongLogTag")
                override fun onCancelled(error: DatabaseError) {
                    Log.e("View Data(ProfSetViewModel)", error.toString())
                    liveData.value = State.HideLoading
                }
            })
        }
    }

    sealed class State(){
        object HideLoading: State()
        object ShowLoading: State()
        data class Result(var name: TextView,
                          var surname: TextView,
                          var email: TextView,
                          var profileImage: CircleImageView): State()
    }


}