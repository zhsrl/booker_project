package com.e.booker.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.e.booker.R
import com.e.booker.model.database.DatabaseProvider
import com.e.booker.model.database.UserDao
import com.e.booker.model.database.UserDatabase
import com.e.booker.model.database.UserEntity
import com.e.booker.utils.SaveDataSharedPreference

class RegistrationActivity : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var userName: EditText
    private lateinit var password: EditText
    private lateinit var register: Button
    private lateinit var haveAccount: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        initAll()

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

                val userDatabase: UserDatabase

                userDatabase = DatabaseProvider.getUserDatabase(applicationContext)

                val userDao: UserDao = userDatabase.userDao()

                Thread(Runnable {
                    kotlin.run {
                        userDao.registerUser(userEntity)
                        runOnUiThread(Runnable {
                            kotlin.run {
                                Toast.makeText(applicationContext, "User registered!", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                }).start()

            }else{
                Toast.makeText(applicationContext, "Мәліметтерді енгізіңіз", Toast.LENGTH_SHORT).show()
            }
        }


        haveAccount.setOnClickListener {
            val intent = Intent(applicationContext, LoginAcitivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        }


    }

    fun validateInput(userEntity: UserEntity): Boolean{
        return !(userEntity.name.isEmpty() || userEntity.userName.isEmpty() || userEntity.password.isEmpty())

    }

    fun initAll(){
        name = findViewById(R.id.reg_nameET)
        userName = findViewById(R.id.reg_usernameET)
        password = findViewById(R.id.reg_passwordET)
        register = findViewById(R.id.reg_btn)
        haveAccount = findViewById(R.id.have_accountTV)
    }
}