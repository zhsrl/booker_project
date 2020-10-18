package com.e.booker.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
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
    private lateinit var userName: EditText
    private lateinit var password: EditText
    private lateinit var register: Button
    private lateinit var haveAccount: TextView
    private lateinit var registrationViewModel: RegistrationViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        //View Model
        val viewModelProviderFactory = ViewModelProviderFactory(context = this)
        registrationViewModel = ViewModelProvider(this, viewModelProviderFactory).get(RegistrationViewModel::class.java)

        initAll()

        //SharedPreference logic
        if(SaveDataSharedPreference.getLoggedStatus(applicationContext)){
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
        }else{
            Toast.makeText(applicationContext, "Logged: false", Toast.LENGTH_SHORT).show()
        }

        register.setOnClickListener{
            val userEntity = UserEntity()

            userEntity.name = name.text.toString()
            userEntity.password = password.text.toString()
            userEntity.userName = userName.text.toString()

            if(validateInput(userEntity)){
                //method from ViewModel
                registrationViewModel.registerUser(userEntity)
            }else{
                Toast.makeText(applicationContext, "Мәліметтерді енгізіңіз", Toast.LENGTH_SHORT).show()
            }
        }

        //Have you account?
        haveAccount.setOnClickListener {
            val intent = Intent(applicationContext, LoginAcitivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        }


    }
    //Test for validate input
    fun validateInput(userEntity: UserEntity): Boolean{
        return !(userEntity.name.isEmpty() || userEntity.userName.isEmpty() || userEntity.password.isEmpty())

    }

    //Initalization elements
    fun initAll(){
        name = findViewById(R.id.reg_nameET)
        userName = findViewById(R.id.reg_usernameET)
        password = findViewById(R.id.reg_passwordET)
        register = findViewById(R.id.reg_btn)
        haveAccount = findViewById(R.id.have_accountTV)
    }
}