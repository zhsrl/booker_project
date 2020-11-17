package com.e.booker.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.e.booker.R
import com.e.booker.model.database.DatabaseProvider
import com.e.booker.model.database.UserEntity
import com.e.booker.utils.SaveDataSharedPreference
import com.e.booker.viewmodel.LoginViewModel
import com.e.booker.viewmodel.ViewModelProviderFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginAcitivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var signInButton: Button
    private lateinit var signUp: TextView
    private lateinit var progressBar: ProgressBar

    private lateinit var loginViewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //ViewModel
        val viewModelProviderFactory = ViewModelProviderFactory(context = this)
        loginViewModel = ViewModelProvider(this, viewModelProviderFactory).get(LoginViewModel::class.java)

        //Initalization all elements
        initAll()

        //Login Logic
        signInButton.setOnClickListener{
            val mEmail = email.editableText.toString()
            val mPassword = password.editableText.toString()

            loginViewModel.loginUser(mEmail, mPassword)
        }

        progressBar.visibility = View.GONE
        loginViewModel.liveData.observe(this, {
            result ->
            when(result){
                is LoginViewModel.State.ShowLoading ->{
                    progressBar.visibility = View.VISIBLE
                }
                is LoginViewModel.State.HideLoading ->{
                    progressBar.visibility = View.GONE
                }
            }
        })

        signUp.setOnClickListener {
            val intent = Intent(applicationContext,RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun initAll(){
        email = findViewById(R.id.signInEmailET)
        password = findViewById(R.id.signInPasswordET)
        signInButton = findViewById(R.id.signInBTN)
        signUp = findViewById(R.id.signUpTV)
        progressBar = findViewById(R.id.signInProgressBar)
    }

    override fun onStart() {
        super.onStart()
        val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        if(currentUser != null){
        }
    }




}