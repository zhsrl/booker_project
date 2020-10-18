package com.e.booker.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.e.booker.R
import com.e.booker.model.database.DatabaseProvider
import com.e.booker.model.database.UserEntity
import com.e.booker.utils.SaveDataSharedPreference
import com.e.booker.viewmodel.LoginViewModel
import com.e.booker.viewmodel.ViewModelProviderFactory

class LoginAcitivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var signInButton: Button

    private lateinit var loginViewModel: LoginViewModel

    var userEntity: UserEntity = UserEntity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //ViewModel
        val viewModelProviderFactory = ViewModelProviderFactory(context = this)
        loginViewModel = ViewModelProvider(this, viewModelProviderFactory).get(LoginViewModel::class.java)

        //Initalization all elements
        initAll()

        if(SaveDataSharedPreference.getLoggedStatus(applicationContext)){
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
        }else{
            Toast.makeText(applicationContext, "Logged: false", Toast.LENGTH_SHORT).show()
        }

        //Login Logic
        signInButton.setOnClickListener{

            val usernameText = username.text.toString()
            val passwordText = password.text.toString()

            if(usernameText.isEmpty() || passwordText.isEmpty()){
                Toast.makeText(applicationContext, "Empty fields!", Toast.LENGTH_SHORT).show()
            }else{
                loginViewModel.loginUser(userEntity,username = usernameText,password = passwordText)

            }
        }

    }

    fun initAll(){
        username = findViewById(R.id.sign_in_usernameET)
        password = findViewById(R.id.sign_in_passwordET)
        signInButton = findViewById(R.id.sign_in_btn)
    }
}