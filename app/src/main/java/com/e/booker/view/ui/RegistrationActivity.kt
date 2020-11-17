package com.e.booker.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.e.booker.R
import com.e.booker.model.database.DatabaseProvider
import com.e.booker.model.database.UserDao
import com.e.booker.model.database.UserDatabase
import com.e.booker.model.database.UserEntity
import com.e.booker.utils.SaveDataSharedPreference
import com.e.booker.viewmodel.RegistrationViewModel
import com.e.booker.viewmodel.ViewModelProviderFactory

class RegistrationActivity : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var signUp: Button
    private lateinit var signIn: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var registrationViewModel: RegistrationViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        //View Model
        val viewModelProviderFactory = ViewModelProviderFactory(context = this)
        registrationViewModel = ViewModelProvider(this, viewModelProviderFactory).get(RegistrationViewModel::class.java)

        initAll()

        //Sign up logic
        signUp.setOnClickListener{
            val mName = name.editableText.toString()
            val mEmail = email.editableText.toString()
            val mPassword = password.editableText.toString()
            registrationViewModel.signUpUser(mName, mEmail, mPassword)
        }

        //Have you account?
        signIn.setOnClickListener {
            val intent = Intent(applicationContext, LoginAcitivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        }

        progressBar.visibility = View.GONE
        registrationViewModel.liveData.observe(this, Observer {
            result ->
            when(result){
                is RegistrationViewModel.State.HideLoading ->{
                    progressBar.visibility = View.GONE
                }
                is RegistrationViewModel.State.ShowLoading ->{
                    progressBar.visibility = View.VISIBLE
                }
            }
        })


    }


    //Initalization elements
    fun initAll(){
        name = findViewById(R.id.signUpNameET)
        email = findViewById(R.id.signUpEmailET)
        password = findViewById(R.id.signUpPasswordET)
        signUp = findViewById(R.id.signUpBTN)
        signIn = findViewById(R.id.signInTV)
        progressBar = findViewById(R.id.signUpProgressBar)
    }
}